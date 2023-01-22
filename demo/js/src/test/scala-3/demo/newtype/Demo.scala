package demo.newtype

import demo.newtype.Preamble._
import slinky.core.WithAttrs
import slinky.web.html.li

object Preamble:

  def tag: WithAttrs[li.tag.type] = li("Apple")

  final case class Props(name: String)

  val typedComponent: TypedFunctionalComponent[Props, li.tag.type] =
    TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(props.name)
    }

  val typedReactElement: TypedReactElement[li.tag.type] = tag

  def typedKeyAddingStage: TypedKeyAddingStage[li.tag.type] = typedComponent(Props("Apple"))

object Demo:

  val item: TypedReactElement[li.tag.type] = tag
