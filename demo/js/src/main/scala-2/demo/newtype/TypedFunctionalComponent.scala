package demo.newtype

import slinky.core.{FunctionalComponent, FunctionalComponentName}

object TypedFunctionalComponent {

  @inline def apply[Props, Result](
    fn: Props => TypedReactElement[Result]
  )(implicit name: FunctionalComponentName): TypedFunctionalComponent[Props, Result] =
    FunctionalComponent(fn).asInstanceOf[TypedFunctionalComponent[Props, Result]]
}
