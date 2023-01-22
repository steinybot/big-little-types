package bll

import bll.TypedKeyableChildrenImplNewTypeUnsafe5Spec.*
import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.*
import slinky.core.*
import slinky.core.facade.ReactElement
import slinky.web.html.{li, style, ul}
import slinky.web.{ReactDOM, html}

import scala.scalajs.js

// https://failex.blogspot.com/2017/04/the-high-cost-of-anyval-subclasses.html

object TypedKeyableChildrenImplNewTypeUnsafe5Spec {

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def fromTag[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def fromKeyAddingStage[Result](stage: TypedKeyAddingStage[Result]): TypedReactElement[Result] =
      new TypedReactElement(stage)

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedReactElement[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.element))
  }

  type Base = {
    type __TypedKeyAddingStage
  }

  sealed trait Tag[Result] extends Any

  object Tag {

  }

  opaque type TypedKeyAddingStage[Result] = Base with KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage {
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]
  }

  extension [Result](stage: TypedKeyAddingStage[Result]) {

    @inline def withKeyTyped(key: String): TypedReactElement[Result] =
      new TypedReactElement[Result](stage.asInstanceOf[KeyAddingStage].withKey(key))
  }

  type TypedFunctionalComponent[Props, Result] =
    FunctionalComponentCore[
      Props,
      TypedKeyAddingStage[Result],
      FunctionalComponent[Props]
    ]

  object TypedFunctionalComponent {

    def apply[Props, Result](fn: Props => TypedReactElement[Result])
                            (implicit name: FunctionalComponentName):
      TypedFunctionalComponent[Props, Result] =
        FunctionalComponent(fn.andThen(_.element))
          .asInstanceOf[TypedFunctionalComponent[Props, Result]]
  }
}

class TypedKeyableChildrenImplNewTypeUnsafe5Spec extends AnyFlatSpec {

  behavior of "Typed keyable children"

  it should "have a conversion to TypedReactElement" in {
    def RedList(children: TypedReactElement[li.tagType]*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    object QuantifiedListItem {
      case class Props(amount: Int, children: String)
      val component = FunctionalComponent[Props] { props =>
        li(s"${props.children} * ${props.amount}")
      }
      def apply(amount: Int)(children: String) = component(Props(amount, children))
    }

    // TODO: This isn't covered in the slides.
    // You can put whatever type you like here, there is no check.
    val cherriesStage: TypedKeyAddingStage[li.tagType] =
    TypedKeyAddingStage.unsafe(QuantifiedListItem(5)("Cherries"))

    val cherriesWithKey: TypedReactElement[li.tagType] =
      cherriesStage.withKeyTyped("cherries")

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

  it should "do something" in {
    def RedList(children: TypedReactElement[li.tag.type]*) =
      ul(style := js.Dynamic.literal(color = "red"))(children: Seq[ReactElement])

    object QuantifiedListItem {
      case class Props(amount: Int, children: String)
      val component = TypedFunctionalComponent[Props, li.tag.type] { props =>
        li(s"${props.children} * ${props.amount}")
      }
      def apply(amount: Int)(children: String) = component(Props(amount, children))
    }

    def App: ReactElement =
      RedList(
        li("Apple", html.key := "apple"),
        li("Banana", html.key := "banana"),
        QuantifiedListItem(5)("Cherries").withKeyTyped("cherries")
      )

    val container = document.createElement("div")
    ReactDOM.render(App, container)
    container.innerHTML mustBe """<ul style="color: red;"><li>Apple</li><li>Banana</li><li>Cherries * 5</li></ul>"""
  }
}