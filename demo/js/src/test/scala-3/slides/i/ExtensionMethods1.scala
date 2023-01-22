package slides.i

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.c.ValueClass1.TypedReactElement
import slinky.core.FunctionalComponent
import slinky.web.html.{key, li, style, ul}

import scala.scalajs.js

object ExtensionMethods1:

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  object QuantifiedListItem:
    case class Props(amount: Int, children: String)
    val component = FunctionalComponent[Props] { props =>
      li(s"${props.children} * ${props.amount}")
    }
    def apply(amount: Int)(children: String) = component(Props(amount, children))

class ExtensionMethods1 extends AnyFlatSpec:

  import ExtensionMethods1._

  behavior of "ExtensionMethods1"

  it should "fail to compile when the tag type is wrong" in {
    """
    def App =
      RedList(
        li("Apple", key := "apple"),
        li("Banana", key := "banana"),
        QuantifiedListItem(5)("Cherries").withKey("cherries") // The type was lost 🤦
      )
    """ shouldNot typeCheck
  }
