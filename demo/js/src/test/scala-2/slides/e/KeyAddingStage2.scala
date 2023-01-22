package slides.e

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.c.ValueClass1.TypedReactElement
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html.{key, li, style, ul}

import scala.scalajs.js

object KeyAddingStage2 {

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  @react
  object QuantifiedListItem {
    case class Props(amount: Int, children: String)

    val component = FunctionalComponent[Props] { props =>
      li(s"${props.children} * ${props.amount}")
    }
  }
}

class KeyAddingStage2 extends AnyFlatSpec {

  import KeyAddingStage2._

  behavior of "KeyAddingStage2"

  it should "fail to compile when the tag type is wrong" in {
    """
    def App =
      RedList(
        li("Apple", key := "apple"),
        li("Banana", key := "banana"),
        QuantifiedListItem(5)("Cherries").withKey("cherries")
      )
    """ shouldNot typeCheck
  }
}
