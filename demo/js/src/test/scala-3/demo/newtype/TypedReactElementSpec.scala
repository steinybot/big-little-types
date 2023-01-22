package demo.newtype

import demo.newtype.ReactSpec.checkHtml
import org.scalatest.flatspec.AnyFlatSpec
import slinky.core.facade.ReactElement
import slinky.web.html.li

class TypedReactElementSpec extends AnyFlatSpec:

  import Preamble._

  behavior of "TypedReactElement"

  it should "be a ReactElement" in {
    val result: ReactElement = typedReactElement
    checkHtml(result, "<li>Apple</li>")
  }

  it should "be implicitly converted from a tag" in {
    val result: TypedReactElement[li.tag.type] = tag
    checkHtml(result, "<li>Apple</li>")
  }

  it should "be implicitly converted from a key adding stage" in {
    val result: TypedReactElement[li.tag.type] = typedKeyAddingStage
    checkHtml(result, "<li>Apple</li>")
  }

  it should "be able to go into a sequence and back out again" in {
    val result: TypedReactElement[li.tag.type] = Seq(typedReactElement).head
    checkHtml(result, "<li>Apple</li>")
  }
