package slides.j

import slides.c.ValueClass1.TypedReactElement
import slides.j.TypedFunctionalComponent2.TypedFunctionalComponent
import slinky.core.annotations.react
import slinky.web.html.{key, li, style, ul}

import scala.scalajs.js

object TypedFunctionalComponent3 {

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  @react
  object QuantifiedListItem {
    case class Props(amount: Int, children: String)

    val component = TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(s"${props.children} * ${props.amount}")
    }
  }

  def App =
    RedList(
      li("Apple", key := "apple"),
      li("Banana", key := "banana"),
      QuantifiedListItem(5)("Cherries").withKeyTyped("cherries")
    )
}
