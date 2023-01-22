package demo.valueclass

import slinky.core.WithAttrs
import slinky.web.html.{li, p}

object Preamble:

  def tag: WithAttrs[li.tag.type] = li("Apple")
  def wrongTag: WithAttrs[p.tag.type] = p("Apple")

  final case class Props(name: String)

  val typedComponent: TypedFunctionalComponent[Props, li.tag.type] =
    TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(props.name)
    }
  val wrongTypedComponent: TypedFunctionalComponent[Props, p.tag.type] =
    TypedFunctionalComponent[Props, p.tag.type] { props =>
      p(props.name)
    }
  val typedComponentWrongProps: TypedFunctionalComponent[String, li.tag.type] =
    TypedFunctionalComponent[String, li.tag.type] { props =>
      li(props)
    }

  val typedReactElement: TypedReactElement[li.tag.type] = tag
  val wrongTypedReactElement: TypedReactElement[p.tag.type] = wrongTag

  def typedKeyAddingStage: TypedKeyAddingStage[li.tag.type] = typedComponent(Props("Apple"))
  def wrongTypedKeyAddingStage: TypedKeyAddingStage[p.tag.type] = wrongTypedComponent(Props("Apple"))
