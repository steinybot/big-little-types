package slides.i

import slides.c.ValueClass1.TypedReactElement
import slinky.core.KeyAddingStage

object ExtensionMethods2:

  type Base = {
    type __TypedKeyAddingStage
  }

  trait Tag[Result]

  object Tag:
    extension [Result](stage: TypedKeyAddingStage[Result])
      @inline def withKeyTyped(key: String): TypedReactElement[Result] =
        new TypedReactElement[Result](stage.asInstanceOf[KeyAddingStage].withKey(key))

  type TypedKeyAddingStage[Result] = Base with KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage:
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]

