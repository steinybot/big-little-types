package demo

package object opaquetype:

  type TypedReactElement[Result] = TypedReactElements.TypedReactElement[Result]

  type TypedKeyAddingStage[Result] = TypedKeyAddingStage.Type[Result]

  type TypedFunctionalComponent[Props, Result] = TypedFunctionalComponent.Type[Props, Result]
