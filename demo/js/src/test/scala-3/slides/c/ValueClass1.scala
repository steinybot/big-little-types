package slides.c

import slinky.core.{ReactElementContainer, WithAttrs}
import slinky.core.facade.ReactElement
import slinky.web.html.li

object ValueClass1:

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement:
    given fromTag[Result]: Conversion[WithAttrs[Result], TypedReactElement[Result]] =
      new TypedReactElement(_)

    given Conversion[TypedReactElement[?], ReactElement] =
      _.asInstanceOf[ReactElement]

  val tag: WithAttrs[li.tag.type] = li("Apple")

  val renderedTypedTag: TypedReactElement[li.tag.type] = tag

