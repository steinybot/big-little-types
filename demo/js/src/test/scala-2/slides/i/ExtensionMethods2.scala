package slides.i

import slides.c.ValueClass1.TypedReactElement
import slinky.core.KeyAddingStage

object ExtensionMethods2 {

  type Base = {
    type __TypedKeyAddingStage
  }

  trait Tag

  object Tag {
    implicit class Ops[Result](stage: TypedKeyAddingStage[Result]) {
      @inline def withKeyTyped(key: String): TypedReactElement[Result] =
        new TypedReactElement[Result](stage.asInstanceOf[KeyAddingStage].withKey(key))
    }

    // TODO: This isn't in the slides.
    implicit def toTypedReactElement[Result](stage: TypedKeyAddingStage[Result]) =
      KeyAddingStage.build(stage).asInstanceOf[TypedReactElement[Result]]
  }

  type TypedKeyAddingStage[Result] = Base with KeyAddingStage with Tag

  object TypedKeyAddingStage {
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]
  }

}
