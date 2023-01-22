package slides.a

import slinky.core.facade.ReactElement
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

object Slinky3 {

  def RedList(children: ReactElement*) =
    ul(style := js.Dynamic.literal(color = "red"))(children: _*)

  def QuantifiedListItem(amount: Int)(children: String) =
    li(s"$children * $amount")

  def App =
    RedList(
      li("Apple"),
      li("Banana"),
      QuantifiedListItem(5)("Cherries")
    )

}
