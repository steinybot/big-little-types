package slides.b

import demo.newtype.TypedReactElement
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slinky.web.html.{li, p, style, ul}

import scala.scalajs.js

object TypedChildren3:

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children: _*)

  def QuantifiedListItem(amount: Int)(children: String) =
    li(s"$children * $amount")

class TypedChildren3 extends AnyFlatSpec:

  import TypedChildren3._

  behavior of "TypedChildren3"

  it should "fail to compile when the tag type is wrong" in {
    """
    def App =
      RedList(
        li("Apple"),
        p("Banana"), // This fails to compile 🥳
        QuantifiedListItem(5)("Cherries")
      )
    """ shouldNot typeCheck
  }
