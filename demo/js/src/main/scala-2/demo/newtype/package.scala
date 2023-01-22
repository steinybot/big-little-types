package demo

import slinky.core.{FunctionalComponent, FunctionalComponentCore}

package object newtype {

  type TypedReactElement[Result] = TypedReactElement.Type[Result]

  type TypedKeyAddingStage[Result] = TypedKeyAddingStage.Type[Result]

  type TypedFunctionalComponent[Props, Result] =
    FunctionalComponentCore[Props, TypedKeyAddingStage[Result], FunctionalComponent[Props]]

}
