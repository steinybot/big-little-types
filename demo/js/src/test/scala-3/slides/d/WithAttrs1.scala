package slides.d

import org.scalatest.flatspec.AnyFlatSpec
import slinky.core.WithAttrs
import slinky.web.html.{li, p, style, ul}
import org.scalatest.matchers.should.Matchers._

import scala.scalajs.js

object WithAttrs1 {

  def RedList(children: WithAttrs[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children: _*)

  def QuantifiedListItem(amount: Int)(children: String) =
    li(s"$children * $amount")

}

class WithAttrs1 extends AnyFlatSpec {

  import WithAttrs1._

  behavior of "WithAttrs1"

  it should "fail to compile when the tag type is wrong" in {
    """
    def App =
      RedList(
        li("Apple"),
        p("Banana"), // This also fails to compile 🤔
        QuantifiedListItem(5)("Cherries")
      )
    """ shouldNot typeCheck
  }
}
