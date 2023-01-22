package slides.j

import slides.c.ValueClass1.TypedReactElement
import slides.j.TypedFunctionalComponent1.TypedFunctionalComponent
import slinky.core.FunctionalComponent

object TypedFunctionalComponent2 {

  object TypedFunctionalComponent {
    def apply[Props, Result](fn: Props => TypedReactElement[Result]):
    TypedFunctionalComponent[Props, Result] =
      FunctionalComponent(fn.andThen(_.element))
        .asInstanceOf[TypedFunctionalComponent[Props, Result]]
  }
}
