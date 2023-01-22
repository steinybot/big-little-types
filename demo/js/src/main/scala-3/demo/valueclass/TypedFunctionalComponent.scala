package demo.valueclass

import slinky.core.{FunctionalComponent, FunctionalComponentCore, FunctionalComponentName, KeyAddingStage}

object TypedFunctionalComponent:

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __TypedFunctionalComponent
  }

  sealed trait Tag

  object Tag:

    given toFunctionalComponentCore[Props, Result]: Conversion[
      TypedFunctionalComponent[Props, Result],
      FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]]
    ] = _.asInstanceOf[FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]]]

  type Type[Props, Result] <: Base with FunctionalComponentCore[Props, TypedKeyAddingStage[Result], FunctionalComponent[Props]] with Tag

  @inline def apply[Props, Result](fn: Props => TypedReactElement[Result])(
    using FunctionalComponentName
  ): TypedFunctionalComponent[Props, Result] =
    FunctionalComponent(fn.andThen(_.element)).asInstanceOf[TypedFunctionalComponent[Props, Result]]

