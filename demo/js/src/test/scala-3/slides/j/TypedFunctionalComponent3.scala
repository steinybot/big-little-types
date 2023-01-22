package slides.j

import slides.c.ValueClass1.TypedReactElement
import slides.j.TypedFunctionalComponent2.TypedFunctionalComponent
import slinky.web.html.{key, li, style, ul}

import scala.scalajs.js

object TypedFunctionalComponent3:

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  object QuantifiedListItem:
    case class Props(amount: Int, children: String)
    val component = TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(s"${props.children} * ${props.amount}")
    }
    def apply(amount: Int)(children: String) = component(Props(amount, children))

  def App =
    RedList(
      li("Apple", key := "apple"),
      li("Banana", key := "banana"),
      QuantifiedListItem(5)("Cherries").withKeyTyped("cherries")
    )
