package demo.newtype

import slinky.core.KeyAddingStage

object TypedKeyAddingStage:

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __TypedKeyAddingStage
  }

  sealed trait Tag[Result] extends Any

  // TODO: Can Result be covariant?
  type Type[Result] <: Base with KeyAddingStage with Tag[Result]

  @inline implicit def render[Props, Result](
    props: Props
  )(implicit component: TypedFunctionalComponent[Props, Result]): TypedKeyAddingStage[Result] =
    component(props)