package slides.k

import slides.c.ValueClass1.TypedReactElement
import slides.j.TypedFunctionalComponent2.TypedFunctionalComponent
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

object Refined1:

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
      li("Apple"),
      li("Banana"),
      QuantifiedListItem(-2)(" ")
    )
