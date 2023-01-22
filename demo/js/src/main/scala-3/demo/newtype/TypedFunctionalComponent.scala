package demo.newtype

import slinky.core.{FunctionalComponent, FunctionalComponentCore, FunctionalComponentName, KeyAddingStage}

object TypedFunctionalComponent:

  @inline def apply[Props, Result](fn: Props => TypedReactElement[Result])(implicit
      name: FunctionalComponentName
  ): TypedFunctionalComponent[Props, Result] =
    FunctionalComponent(fn).asInstanceOf[TypedFunctionalComponent[Props, Result]]

  // FIXME: This isn't being picked up correctly. Maybe we need a tag here too?
  given toFunctionalComponentCore[Props, Result]: Conversion[
      TypedFunctionalComponent[Props, Result],
      FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]]
  ] = _.asInstanceOf[FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]]]
