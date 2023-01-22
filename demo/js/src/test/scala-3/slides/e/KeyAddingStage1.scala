package slides.e

import slides.c.ValueClass1.TypedReactElement
import slinky.web.html.{key, li, style, ul}

import scala.scalajs.js

object KeyAddingStage1:

  def RedList(children: TypedReactElement[li.tag.type]*) =
    ul(style := js.Dynamic.literal(color = "red"))(children)


  def QuantifiedListItem(amount: Int)(children: String) =
    li(s"$children * $amount")





  def App =
    RedList(
      li("Apple", key := "apple"),
      li("Banana", key := "banana"),
      QuantifiedListItem(5)("Cherries") // Where does the key go?
    )

