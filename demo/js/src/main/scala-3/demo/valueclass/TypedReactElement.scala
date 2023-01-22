package demo.valueclass

import slinky.core.facade.ReactElement
import slinky.core.{KeyAddingStage, WithAttrs}

final class TypedReactElement[Result] private (val element: ReactElement) extends AnyVal

object TypedReactElement:

  def unsafe[Result](element: ReactElement): TypedReactElement[Result] =
    new TypedReactElement[Result](element)

  implicit def fromWithAttrs[Result](result: WithAttrs[Result]): TypedReactElement[Result] =
    unsafe(result())

  implicit def fromKeyAddingStage[Result](result: TypedKeyAddingStage[Result]): TypedReactElement[Result] =
    unsafe(KeyAddingStage.build(result))

  implicit def toReactElement(element: TypedReactElement[_]): ReactElement =
    element.element

