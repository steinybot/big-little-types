package bll

import bll.TypedKeyableChildrenImplNewTypeUnsafe2Spec.{TypedKeyAddingStage, TypedReactElement}
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

object TypedKeyableChildrenImplNewTypeUnsafe2Spec {

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def apply[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedReactElement[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.element))
  }

  type Base = {
    type __TypedKeyAddingStage
  }

  trait Tag[Result]

  type TypedKeyAddingStage[Result] = Base with KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage {
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]
  }
}

class TypedKeyableChildrenImplNewTypeUnsafe2Spec extends AnyFlatSpec {

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

  it should "no conversion to TypedReactElement yet" in {
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

  it should "fail with a org.scalajs.linker.runtime.UndefinedBehaviorError" in {
    def RedList(children: TypedKeyAddingStage[li.tag.type]*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    @react
    object QuantifiedListItem {
      case class Props(amount: Int, children: String)
      val component = FunctionalComponent[Props] { props =>
        li(s"${props.children} * ${props.amount}")
      }
    }

    val cherries: KeyAddingStage =
      QuantifiedListItem(5)("Cherries")

    val typedCherries: TypedKeyAddingStage[li.tagType] =
      TypedKeyAddingStage.unsafe(cherries)

    val fruit: Seq[TypedKeyAddingStage[li.tag.type]] = Seq(typedCherries)

    val firstFruit: TypedKeyAddingStage[li.tag.type] = fruit.head
  }
}