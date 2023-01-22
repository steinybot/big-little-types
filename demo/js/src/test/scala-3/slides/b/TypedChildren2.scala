package slides.b

import slinky.core.facade.ReactElement
import slinky.web.html.{li, p, style, ul}

import scala.scalajs.js

object TypedChildren2:

  def RedList(children: ReactElement*) =
    ul(style := js.Dynamic.literal(color = "red"))(children: _*)

  def QuantifiedListItem(amount: Int)(children: String) =
    li(s"$children * $amount")

  def App =
    RedList(
      li("Apple"),
      p("Banana"), // This compiles fine 😒
      QuantifiedListItem(5)("Cherries")
    )

