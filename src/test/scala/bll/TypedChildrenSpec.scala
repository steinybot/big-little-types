package bll

import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import slinky.core.WithAttrs
import slinky.core.facade.ReactElement
import slinky.web.ReactDOM
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

class TypedChildrenSpec extends AnyFlatSpec with Matchers {

  object TypedReactElement {

    type Base = {
      type __TypedReactElement__newtype
    }

    sealed trait Tag[+Props] extends Any

    type Type[+Result] <: Base with ReactElement with Tag[Result]

    implicit def fromWithAttrs[Result](result: WithAttrs[Result]): TypedReactElement[Result] =
      result().asInstanceOf[TypedReactElement[Result]]

  }

  type TypedReactElement[+Result] = TypedReactElement.Type[Result]

  "Typed children" should "compile if the type is correct" in {
    def RedList(children: TypedReactElement[li.tagType]*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    def QuantifiedListItem(amount: Int)(children: String): TypedReactElement[li.tagType] =
      li(s"$children * $amount")

    def App: ReactElement =
      RedList( //
        li("Apple"),
        li("Banana"),
        QuantifiedListItem(5)("Cherries")
      )

    val container = document.createElement("div")
    ReactDOM.render(App, container)
    container.innerHTML mustBe """<ul style="color: red;"><li>Apple</li><li>Banana</li><li>Cherries * 5</li></ul>"""
  }

  it should "fail to compile if the type is wrong" in {
    def RedList(children: TypedReactElement[li.tagType]*): ReactElement =
      ul(style := js.Dynamic.literal(color = "red"))(children: _*)

    def QuantifiedListItem(amount: Int)(children: String): TypedReactElement[li.tagType] =
      li(s"$children * $amount")

    """
      RedList(
        li("Apple"),
        p("Banana"),
        QuantifiedListItem(5)("Cherries")
      )
    """ mustNot compile
  }
}