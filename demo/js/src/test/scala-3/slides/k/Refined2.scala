package slides.k

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Trimmed
import eu.timepit.refined.types.numeric._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.c.ValueClass1.TypedReactElement
import slides.j.TypedFunctionalComponent2.TypedFunctionalComponent
import slides.k.Refined2Preamble._
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

object Refined2Preamble:

  // This has to be defined in a separate top level class.
  type ItemAmount = PosInt
  type ItemName = String Refined (Trimmed And NonEmpty)

object Refined2:

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  object QuantifiedListItem:
    case class Props(amount: ItemAmount, children: ItemName)
    val component = TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(s"${props.children} * ${props.amount}")
    }
    def apply(amount: ItemAmount)(children: ItemName) = component(Props(amount, children))

class Refined2 extends AnyFlatSpec:

  import Refined2._

  behavior of "Refined2"

  it should "fail to compile when the props are the wrong type" in {
    """
    def App =
      RedList(
        li("Apple"),
        li("Banana"),
        QuantifiedListItem(-2)(" ")
      )
    """ shouldNot typeCheck
  }
