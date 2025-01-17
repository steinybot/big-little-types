package demo.newtype

import slinky.core.{FunctionalComponent, FunctionalComponentCore, FunctionalComponentName, KeyAddingStage}

object TypedFunctionalComponent {

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __TypedFunctionalComponent
  }

  sealed trait Tag

  object Tag {

    implicit def toFunctionalComponentCore[Props, Result](component: TypedFunctionalComponent[Props, Result]):
    FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]] =
      component.asInstanceOf[FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]]]
  }

  type Type[Props, Result] <: Base with FunctionalComponentCore[Props, TypedKeyAddingStage[Result], FunctionalComponent[Props]] with Tag

  @inline def apply[Props, Result](fn: Props => TypedReactElement[Result])(
    implicit name: FunctionalComponentName
  ): TypedFunctionalComponent[Props, Result] =
    FunctionalComponent(fn).asInstanceOf[TypedFunctionalComponent[Props, Result]]

}
