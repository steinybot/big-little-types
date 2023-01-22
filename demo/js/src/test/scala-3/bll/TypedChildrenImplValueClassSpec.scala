package bll

import bll.TypedChildrenImplValueClassSpec.TypedReactElement
import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import slinky.core.facade.ReactElement
import slinky.core.{ReactElementContainer, WithAttrs}
import slinky.web.ReactDOM
import slinky.web.html.{li, style, ul}

import scala.scalajs.js

// https://failex.blogspot.com/2017/04/the-high-cost-of-anyval-subclasses.html

object TypedChildrenImplValueClassSpec {

  class TypedReactElement[Result] private (val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def apply[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def anyToElementContainer[E, F[_]]
    (e: F[E])(implicit f: ReactElementContainer[F], cv: E => TypedReactElement[_]): F[ReactElement] =
      f.map(e)(cv.andThen(_.element))
  }
}

class TypedChildrenImplValueClassSpec extends AnyFlatSpec {

  def RedList(children: TypedReactElement[li.tagType]*): ReactElement =
    ul(style := js.Dynamic.literal(color = "red"))(children: _*)

  def QuantifiedListItem(amount: Int)(children: String): TypedReactElement[li.tagType] =
    li(s"$children * $amount")

  behavior of "Typed children as a value class"

  it should "compile if the type is correct" in {
    def App: ReactElement =
      RedList( //
        // This results in:
        //   49: invokevirtual #217  // Method slinky/web/html/li$.apply:(Lscala/collection/immutable/Seq;)Lscala/scalajs/js/Array;
        //   52: invokevirtual #220  // Method bll/TypedChildrenImplValueClassSpec$TypedReactElement$.apply:(Lscala/scalajs/js/Array;)Lslinky/core/facade/ReactElement;
        //   55: invokespecial #244  // Method bll/TypedChildrenImplValueClassSpec$TypedReactElement."<init>":(Lslinky/core/facade/ReactElement;)V
        //   58: aastore
        //   59: dup
        //   60: iconst_1
        //   61: new           #10   // class bll/TypedChildrenImplValueClassSpec$TypedReactElement
        li("Apple"),
        li("Banana"),
        QuantifiedListItem(5)("Cherries")
      )

    val container = document.createElement("div")
    ReactDOM.render(App, container)
    container.innerHTML mustBe """<ul style="color: red;"><li>Apple</li><li>Banana</li><li>Cherries * 5</li></ul>"""
  }

  it should "fail to compile if the type is wrong" in {
    """
      RedList(
        li("Apple"),
        p("Banana"),
        QuantifiedListItem(5)("Cherries")
      )
    """ mustNot compile
  }
}