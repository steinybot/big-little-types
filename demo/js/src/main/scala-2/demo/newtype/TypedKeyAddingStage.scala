package demo.newtype

import slinky.core.KeyAddingStage

object TypedKeyAddingStage {

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __TypedKeyAddingStage
  }

  sealed trait Tag[Result] extends Any

  object Tag {
    implicit class Ops[Result](stage: TypedKeyAddingStage[Result]) {
      @inline def withKeyTyped(key: String): TypedReactElement[Result] =
        stage.asInstanceOf[KeyAddingStage].withKey(key).asInstanceOf[TypedReactElement[Result]]
    }
  }

    // TODO: Can Result be covariant?
  type Type[Result] <: Base with KeyAddingStage with Tag[Result]

  // TODO: How does this get found but the Ops doesn't unless it is put on the Tag?
  @inline implicit def render[Props, Result](props: Props)(implicit
      component: TypedFunctionalComponent[Props, Result]
  ): TypedKeyAddingStage[Result] =
    component(props)
}
