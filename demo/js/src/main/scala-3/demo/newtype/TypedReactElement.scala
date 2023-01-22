package demo.newtype

import slinky.core.facade.ReactElement
import slinky.core.{KeyAddingStage, WithAttrs}

object TypedReactElement:

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __TypedReactElement
  }

  // TODO: Is the type parameter needed?
  sealed trait Tag[Result] extends Any

  // TODO: Can Result be covariant?
  type Type[Result] <: Base with ReactElement with Tag[Result]

  implicit def fromWithAttrs[Result](result: WithAttrs[Result]): TypedReactElement[Result] =
    result().asInstanceOf[TypedReactElement[Result]]

  implicit def fromKeyAddingStage[Result](result: TypedKeyAddingStage[Result]): TypedReactElement[Result] =
    KeyAddingStage.build(result).asInstanceOf[TypedReactElement[Result]]

