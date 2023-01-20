package bll

import bll.TypedChildrenImplValueClassSpec.TypedReactElement
import bll.TypedKeyableChildrenImplValueClassSpec.TypedKeyAddingStage
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.core.{FunctionalComponent, KeyAddingStage, ReactElementContainer, WithAttrs}
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

// https://failex.blogspot.com/2017/04/the-high-cost-of-anyval-subclasses.html

object TypedKeyableChildrenImplValueClassSpec {

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def apply[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedReactElement[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.element))
  }

  // This fails with:
  // value class may not wrap another user-defined value class
  //class TypedKeyAddingStage[Result](val stage: KeyAddingStage) extends AnyVal
  trait TypedKeyAddingStage[Result] {
    def stage: KeyAddingStage
  }

  object TypedKeyAddingStage {
    // No proof that this stage produces a Result!
    def unsafe[Result](stage: KeyAddingStage): TypedKeyAddingStage[Result] =
      null //stage.asInstanceOf[TypedKeyAddingStage[Result]]

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedKeyAddingStage[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.stage))
  }
}

class TypedKeyableChildrenImplValueClassSpec extends AnyFlatSpec {

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

    """
    val cherriesWithKey: ReactElement =
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