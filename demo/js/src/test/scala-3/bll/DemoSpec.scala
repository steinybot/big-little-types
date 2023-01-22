package bll

import bll.DemoSpec.TypedKeyAddingStage
import bll.Thingy.QuantifiedListItem
import org.scalatest.flatspec.AnyFlatSpec
import slinky.core.facade.ReactElement
import slinky.core.{FunctionalComponent, KeyAddingStage, ReactElementContainer, WithAttrs}
import slinky.web.html.li

object Thingy {

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def apply[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedReactElement[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.element))
  }

  object QuantifiedListItem {
    case class Props(amount: Int, children: String)
    val component = FunctionalComponent[Props] { props =>
      li(s"${props.children} * ${props.amount}")
    }
    def apply(amount: Int)(children: String) = component(Props(amount, children))
  }
}

object DemoSpec {

  trait Tag[Result]

  // It doesn't matter which way around we put this.
  //type TypedKeyAddingStage[Result] = KeyAddingStage with Tag[Result]
  //type TypedKeyAddingStage[Result] = Tag[Result] with KeyAddingStage

  opaque type TypedKeyAddingStage[Result] = KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage {
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]
  }
}

class DemoSpec extends AnyFlatSpec {

  behavior of "Typed keyable children"

  it should "fail with something" in {
    val cherries: KeyAddingStage = QuantifiedListItem(5)("Cherries")

    val typedCherries: TypedKeyAddingStage[li.tagType] = TypedKeyAddingStage.unsafe(cherries)

    val fruit: Seq[TypedKeyAddingStage[li.tag.type]] = Seq(typedCherries)

    val firstFruit: TypedKeyAddingStage[li.tag.type] = fruit.head
  }
}