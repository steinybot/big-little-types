package bll

import bll.TypedKeyableChildrenImplScalazSpec.{TypedKeyAddingStage, TypedReactElement}
import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.core.{FunctionalComponent, KeyAddingStage, ReactElementContainer, WithAttrs}
import slinky.web.{ReactDOM, html}
import slinky.web.html.{li, p, style, ul}

import scala.scalajs.js

// https://failex.blogspot.com/2017/04/the-high-cost-of-anyval-subclasses.html

object TypedKeyableChildrenImplScalazSpec {

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def apply[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedReactElement[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.element))
  }

  sealed abstract class TypedKeyAddingStageImpl {
    type T[Result]

    def unsafe[Result](stage: KeyAddingStage): T[Result]
    def unwrap[Result](stage: T[Result]): KeyAddingStage
  }

  object TypedKeyAddingStageImpl {
    implicit class TOps[Result](val stage: TypedKeyAddingStage[Result]) extends AnyVal {
      def withKey(key: String): TypedReactElement[Result] = {
        val element = TypedKeyAddingStage.unwrap(stage).withKey(key)
        new TypedReactElement[Result](element)
      }
    }
  }

  val TypedKeyAddingStage: TypedKeyAddingStageImpl = new TypedKeyAddingStageImpl {
    override type T[Result] = KeyAddingStage

    override def unsafe[Result](stage: KeyAddingStage): T[Result] = stage
    override def unwrap[Result](stage: T[Result]): KeyAddingStage = stage
  }

  type TypedKeyAddingStage[Result] = TypedKeyAddingStage.T[Result]
}

class TypedKeyableChildrenImplScalazSpec extends AnyFlatSpec {

  behavior of "Typed keyable children"

  it should "attempt to use TypedKeyAddingStage" in {
    def RedList(children: TypedReactElement[li.tagType]*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    @react
    object QuantifiedListItem {
      case class Props(amount: Int, children: String)
      val component = FunctionalComponent[Props] { props =>
        li(s"${props.children} * ${props.amount}")
      }
    }

    val cherriesStage: TypedKeyAddingStage[li.tagType] =
      TypedKeyAddingStage.unsafe(QuantifiedListItem(5)("Cherries"))

    val cherriesWithKey: TypedReactElement[li.tagType] =
      cherriesStage.withKey("cherries")

    def App: ReactElement =
      RedList( //
        li("Apple", html.key := "apple"),
        li("Banana", html.key := "banana"),
        cherriesWithKey
      )

    val container = document.createElement("div")
    ReactDOM.render(App, container)
    container.innerHTML mustBe """<ul style="color: red;"><li>Apple</li><li>Banana</li><li>Cherries * 5</li></ul>"""
  }

  it should "fail to compile if the type is wrong" in {
    def RedList(children: TypedReactElement[li.tagType]*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    @react
    object QuantifiedListItem {
      case class Props(amount: Int, children: String)
      val component = FunctionalComponent[Props] { props =>
        // We changed this!
        p(s"${props.children} * ${props.amount}")
      }
    }

    // You can put whatever type you like here, there is no check.
    val cherriesStage: TypedKeyAddingStage[li.tagType] =
      TypedKeyAddingStage.unsafe(QuantifiedListItem(5)("Cherries"))

    val cherriesWithKey: TypedReactElement[li.tagType] =
      cherriesStage.withKey("cherries")

    def App: ReactElement =
      RedList( //
        li("Apple", html.key := "apple"),
        li("Banana", html.key := "banana"),
        cherriesWithKey
      )

    val container = document.createElement("div")
    ReactDOM.render(App, container)
    // This is wrong but expected.
    container.innerHTML mustBe """<ul style="color: red;"><li>Apple</li><li>Banana</li><p>Cherries * 5</p></ul>"""
  }
}