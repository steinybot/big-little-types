package slides.c

import slinky.core.{ReactElementContainer, WithAttrs}
import slinky.core.facade.ReactElement
import slinky.web.html.li

object ValueClass1 {

  class TypedReactElement[Result](val element: ReactElement) extends AnyVal

  object TypedReactElement {
    implicit def fromTag[Result](tag: WithAttrs[Result]): TypedReactElement[Result] =
      new TypedReactElement(tag)

    implicit def toReactElement(result: TypedReactElement[_]): ReactElement =
      result.asInstanceOf[ReactElement]
  }

  val tag: WithAttrs[li.tag.type] = li("Apple")

  val renderedTypedTag: TypedReactElement[li.tag.type] = tag

}
