package demo

package object newtype {

  type TypedReactElement[Result] = TypedReactElement.Type[Result]

  type TypedKeyAddingStage[Result] = TypedKeyAddingStage.Type[Result]

  type TypedFunctionalComponent[Props, Result] = TypedFunctionalComponent.Type[Props, Result]

}
