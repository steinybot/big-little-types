package bll

import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import slinky.core.WithAttrs
import slinky.core.facade.ReactElement
import slinky.web.ReactDOM
import slinky.web.html.{li, p, style, ul}

import scala.scalajs.js

class UntypedChildrenSpec extends AnyFlatSpec {

  behavior of "Untyped children"

  it should "allow any type of child" in {
    def RedList(children: ReactElement*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    def QuantifiedListItem(amount: Int)(children: String): WithAttrs[li.tag.type] =
      li(s"$children * $amount")

    def App: ReactElement =
      RedList( //
        li("Apple"),
        p("Banana"), // This compiles fine 😒
        QuantifiedListItem(5)("Cherries")
      )

    val container = document.createElement("div")
    ReactDOM.render(App, container)
    container.innerHTML mustBe """<ul style="color: red;"><li>Apple</li><p>Banana</p><li>Cherries * 5</li></ul>"""
  }
}