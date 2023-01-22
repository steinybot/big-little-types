package slides.h

import slides.e.KeyAddingStage2.QuantifiedListItem
import slinky.core.KeyAddingStage

object StructuralRefinement1:

  type Base = {
    type __TypedKeyAddingStage
  }

  trait Tag[Result]

  type TypedKeyAddingStage[Result] = Base with KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage:
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]

  val cherries = QuantifiedListItem(5)("Cherries")
  val typedCherries = TypedKeyAddingStage.unsafe(cherries)
  val fruit = Seq(typedCherries)
  val firstFruit = fruit.head

