package bll

import bll.TypedKeyableChildrenImplNewTypeUnsafeSpec.{TypedKeyAddingStage, TypedReactElement}
import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.core.{FunctionalComponent, KeyAddingStage, ReactElementContainer, WithAttrs}
import slinky.web.html.{li, p, style, ul}
import slinky.web.{ReactDOM, html}

import scala.scalajs.js

// https://failex.blogspot.com/2017/04/the-high-cost-of-anyval-subclasses.html

object TypedKeyableChildrenImplNewTypeUnsafeSpec {

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def apply[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedReactElement[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.element))
  }

  trait Tag[Result]

  type TypedKeyAddingStage[Result] = KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage {
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]
  }
}

class TypedKeyableChildrenImplNewTypeUnsafeSpec extends AnyFlatSpec {

  behavior of "Typed keyable children"

  it should "attempt to use TypedKeyAddingStage" in {
    def RedList(children: ReactElement*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    @react
    object QuantifiedListItem {
      case class Props(amount: Int, children: String)
      val component = FunctionalComponent[Props] { props =>
        li(s"${props.children} * ${props.amount}")
      }
    }

    def App: ReactElement =
      RedList( //
        li("Apple", html.key := "apple"),
        li("Banana", html.key := "banana"),
        TypedKeyAddingStage.unsafe(QuantifiedListItem(5)("Cherries")).withKey("cherries")
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

    """
    val cherriesWithKey: TypedReactElement[li.tagType] =
      cherriesStage.withKey("cherries")

    def App: ReactElement =
      RedList( //
        li("Apple", html.key := "apple"),
        li("Banana", html.key := "banana"),
        cherriesWithKey
      )
    """ mustNot compile
  }
}