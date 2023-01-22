package demo.newtype

import org.scalajs.dom.document
import org.scalatest.Assertion
import slinky.core.facade.ReactElement
import org.scalatest.matchers.must.Matchers._
import slinky.web.ReactDOM

object ReactSpec {

  def checkHtml(element: ReactElement, expectedHtml: String): Assertion = {
    val container = document.createElement("div")
    ReactDOM.render(element, container)
    container.innerHTML mustBe expectedHtml
  }
}
