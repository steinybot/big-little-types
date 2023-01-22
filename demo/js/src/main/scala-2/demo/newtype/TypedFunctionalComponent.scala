package demo.newtype

import slinky.core.{FunctionalComponent, FunctionalComponentCore, FunctionalComponentName, KeyAddingStage}

object TypedFunctionalComponent {

  @inline def apply[Props, Result](fn: Props => TypedReactElement[Result])(
    implicit name: FunctionalComponentName
  ): TypedFunctionalComponent[Props, Result] =
    FunctionalComponent(fn).asInstanceOf[TypedFunctionalComponent[Props, Result]]

  // FIXME: This isn't being picked up correctly. Maybe we need a tag here too?
  implicit def toFunctionalComponentCore[Props](component: TypedFunctionalComponent[Props, _]):
  FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]] =
    component.asInstanceOf[FunctionalComponentCore[Props, KeyAddingStage, FunctionalComponent[Props]]]
}
