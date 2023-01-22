package slides.k

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Trimmed
import eu.timepit.refined.types.numeric._
import slides.c.ValueClass1.TypedReactElement
import slides.j.TypedFunctionalComponent2.TypedFunctionalComponent
import slides.k.Refined3Preamble._
import slinky.core.annotations.react
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

object Refined3Preamble {

  // This has to be defined in a separate top level class.
  type ItemAmount = PosInt
  type ItemName = String Refined (Trimmed And NonEmpty)
}

object Refined3 {

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  @react
  object QuantifiedListItem {
    case class Props(amount: ItemAmount, children: ItemName)
    val component = TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(s"${props.children} * ${props.amount}")
    }
  }

  def App =
    RedList(
      li("Apple"),
      li("Banana"),
      QuantifiedListItem(5)("Cherries")
    )
}
