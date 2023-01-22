package demo.newtype

import slinky.core.facade.ReactElement
import slinky.core.{KeyAddingStage, WithAttrs}

object TypedReactElement {

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __TypedReactElement
  }

  sealed trait Tag extends Any

  // TODO: Can Result be covariant?
  type Type[Result] <: Base with ReactElement with Tag

  def unsafe[Result](element: ReactElement): TypedReactElement[Result] =
    element.asInstanceOf[TypedReactElement[Result]]

  implicit def fromWithAttrs[Result](result: WithAttrs[Result]): TypedReactElement[Result] =
    unsafe(result())

  implicit def fromKeyAddingStage[Result](result: TypedKeyAddingStage[Result]): TypedReactElement[Result] =
    unsafe(KeyAddingStage.build(result))

}
