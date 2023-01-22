package demo.newtype

import demo.newtype.ReactSpec.checkHtml
import org.scalatest.flatspec.AnyFlatSpec
import slinky.core.{FunctionalComponent, FunctionalComponentCore, KeyAddingStage}
import slinky.web.html.li

class TypedFunctionalComponentSpec extends AnyFlatSpec:

  import Preamble._

  behavior of "TypedFunctionalComponent"

  it should "be a FunctionalComponentCore" in {
    // FIXME: This should be implicit.
    val result: FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]] =
      TypedFunctionalComponent.toFunctionalComponentCore(typedComponent)
    checkHtml(result(Props("Apple")), "<li>Apple</li>")
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
