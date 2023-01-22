package demo.valueclass

import demo.valueclass.ReactSpec.checkHtml
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slinky.core.{FunctionalComponent, FunctionalComponentCore, KeyAddingStage}
import slinky.web.html.li

class TypedFunctionalComponentSpec extends AnyFlatSpec:

  import Preamble._

  behavior of "TypedFunctionalComponent"

  it should "be a FunctionalComponentCore" in {
    val result: FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]] = typedComponent
    checkHtml(result(Props("Apple")), "<li>Apple</li>")
  }

  it should "fail to be a FunctionalComponentCore if the props are wrong" in {
    """val result: FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]] = typedComponentWrongProps""" shouldNot typeCheck
  }

  it should "fail to compile if the props are wrong" in {
    """val result: TypedFunctionalComponent[Props, li.tag.type] = typedComponentWrongProps""" shouldNot typeCheck
  }

  it should "fail to compile if the result is wrong" in {
    """val result: TypedFunctionalComponent[Props, li.tag.type] = wrongTypedComponent""" shouldNot typeCheck
  }

  it should "render to a TypedKeyAddingStage" in {
    val result: TypedKeyAddingStage[li.tag.type] = typedComponent(Props("Apple"))
    checkHtml(result, "<li>Apple</li>")
  }

  it should "be transitive" in {
    val component = TypedFunctionalComponent[Props, li.tag.type] { props =>
      typedComponent(props)
    }
    val result: TypedKeyAddingStage[li.tag.type] = component(Props("Apple"))
    checkHtml(result, "<li>Apple</li>")
  }
