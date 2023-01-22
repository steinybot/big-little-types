package slides.j

import slides.i.ExtensionMethods2.TypedKeyAddingStage
import slinky.core.{FunctionalComponent, FunctionalComponentCore}

object TypedFunctionalComponent1:

  type TypedFunctionalComponent[Props, Result] =
    FunctionalComponentCore[
      Props,
      TypedKeyAddingStage[Result],
      FunctionalComponent[Props]
    ]
