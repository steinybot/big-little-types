package slides.k

import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.auto.*
import eu.timepit.refined.boolean.*
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Trimmed
import eu.timepit.refined.types.numeric.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import slides.c.ValueClass1.TypedReactElement
import slides.j.TypedFunctionalComponent2.TypedFunctionalComponent
import slides.k.Refined3Preamble.*
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

object Refined3Preamble:

  // This has to be defined in a separate top level class.
  type ItemAmount = PosInt
  val ItemAmount = PosInt
  type ItemName = String Refined (Trimmed And NonEmpty)
  object ItemName extends RefinedTypeOps[ItemName, String]

object Refined3:

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  object QuantifiedListItem:
    case class Props(amount: ItemAmount, children: ItemName)
    val component = TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(s"${props.children} * ${props.amount}")
    }
    def apply(amount: ItemAmount)(children: ItemName) = component(Props(amount, children))

class Refined3 extends AnyFlatSpec:

  behavior of "Refined3"

  // No value macros for Scala 3 yet. See https://github.com/fthomas/refined/issues/932
  it should "fail to compile when the tag type is wrong" in pendingUntilFixed {
    """
    def App =
      RedList(
        li("Apple"),
        li("Banana"),
        QuantifiedListItem(5)("Cherries")
      )
    """ should compile
  }
