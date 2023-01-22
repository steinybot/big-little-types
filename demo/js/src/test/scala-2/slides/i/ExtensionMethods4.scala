package slides.i

import slides.c.ValueClass1.TypedReactElement
import slides.i.ExtensionMethods2.TypedKeyAddingStage
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html.{key, li, style, ul}

import scala.scalajs.js

object ExtensionMethods4 {

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)

  @react
  object QuantifiedListItem {
    case class Props(amount: Int, children: String)

    val component = FunctionalComponent[Props] { props =>
      li(s"${props.children} * ${props.amount}")
    }
  }

  def App =
    RedList(
      li("Apple", key := "apple"),
      li("Banana", key := "banana"),
      TypedKeyAddingStage.unsafe(QuantifiedListItem(5)("Cherries"))
        .withKeyTyped("cherries")
    )
}
