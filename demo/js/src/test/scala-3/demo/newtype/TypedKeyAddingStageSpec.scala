package demo.newtype

import demo.newtype.ReactSpec.checkHtml
import org.scalatest.flatspec.AnyFlatSpec
import slinky.core.KeyAddingStage
import slinky.core.facade.ReactElement
import slinky.web.html.li

class TypedKeyAddingStageSpec extends AnyFlatSpec:

  import Preamble._

  behavior of "TypedKeyAddingStage"

  it should "be a KeyAddingStage" in {
    val result: KeyAddingStage = typedKeyAddingStage
    checkHtml(result, "<li>Apple</li>")
  }

  it should "implicitly render props with a matching typed component" in {
    given TypedFunctionalComponent[Props, li.tag.type] = typedComponent
    val result: TypedKeyAddingStage[li.tag.type] = Props("Apple")
    checkHtml(result, "<li>Apple</li>")
  }

  it should "be able to take a key" in {
    val result: TypedReactElement[li.tag.type] = typedKeyAddingStage.withKeyTyped("apple")
    checkHtml(result, "<li>Apple</li>")
  }

  it should "be able to go into a sequence and back out again" in {
    val result: TypedKeyAddingStage[li.tag.type] = Seq(typedKeyAddingStage).head
    checkHtml(result, "<li>Apple</li>")
  }
