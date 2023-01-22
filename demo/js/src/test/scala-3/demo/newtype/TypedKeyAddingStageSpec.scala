package demo.newtype

import demo.newtype.ReactSpec.checkHtml
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import slinky.core.KeyAddingStage
import slinky.web.html.{li, p}

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

  it should "fail to compile if the implicit typed component is wrong" in {
    given TypedFunctionalComponent[Props, p.tag.type] = wrongTypedComponent
    """val result: TypedKeyAddingStage[li.tag.type] = Props("Apple")""" shouldNot typeCheck
  }

  it should "be able to take a key" in {
    val result: TypedReactElement[li.tag.type] = typedKeyAddingStage.withKeyTyped("apple")
    checkHtml(result, "<li>Apple</li>")
  }

  it should "fail to compile if the result is wrong" in {
    """val result: TypedKeyAddingStage[li.tag.type] = wrongTypedKeyAddingStage""" shouldNot typeCheck
  }

  it should "be able to go into a sequence and back out again" in {
    val result: TypedKeyAddingStage[li.tag.type] = Seq(typedKeyAddingStage).head
    checkHtml(result, "<li>Apple</li>")
  }
