package bll

import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import slinky.core.{FunctionalComponent, KeyAddingStage, WithAttrs}
import slinky.core.facade.ReactElement
import slinky.web.ReactDOM
import slinky.web.html
import slinky.web.html.{li, p, style, ul}

import scala.scalajs.js

class UntypedKeyableChildrenSpec extends AnyFlatSpec {

  behavior of "Untyped keyable children"

  it should "demonstrate why we need KeyAddingStage" in {
    def RedList(children: ReactElement*): ReactElement = {
      ul(style := js.Dynamic.literal(color = "red"))(children)
    }

    def QuantifiedListItem(amount: Int)(children: String): WithAttrs[li.tag.type] =
      li(s"$children * $amount")

    """
    def App: ReactElement =
      RedList( //
        li("Apple", html.key := "apple"),
        p("Banana", html.key := "banana"),
        QuantifiedListItem(5)("Cherries").withKey("cherries")
      )
    """ mustNot compile
  }

  it should "show the purpose of FunctionalComponent" in {
    def RedList(children: ReactElement*): ReactElement = {
      ul(style := js.Dynamic.literal(color = "red"))(children)
    }

    object QuantifiedListItem {
      case class Props(amount: Int, children: String)
      val component = FunctionalComponent[Props] { props =>
        li(s"${props.children} * ${props.amount}")
      }
      def apply(amount: Int)(children: String) = component(Props(amount, children))
    }

    val cherriesStage: KeyAddingStage = QuantifiedListItem(5)("Cherries")

    def App: ReactElement =
      RedList( //
        li("Apple", html.key := "apple"),
        p("Banana", html.key := "banana"),
        cherriesStage.withKey("cherries")
      )

    val container = document.createElement("div")
    ReactDOM.render(App, container)
    container.innerHTML mustBe """<ul style="color: red;"><li>Apple</li><p>Banana</p><li>Cherries * 5</li></ul>"""
  }
}