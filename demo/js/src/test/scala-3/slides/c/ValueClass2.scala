package slides.c

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.c.ValueClass1.TypedReactElement
import slinky.web.html.{li, p, style, ul}

import scala.scalajs.js

object ValueClass2 {

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children: _*)

  def QuantifiedListItem(amount: Int)(children: String) =
    li(s"$children * $amount")

}

class ValueClass2 extends AnyFlatSpec {

  import ValueClass2._

  behavior of "ValueClass2"

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
}
