package demo.opaquetype

import slinky.core.facade.ReactElement
import slinky.core.{KeyAddingStage, WithAttrs}

object TypedReactElements:

  // TODO: Can Result be covariant?
  opaque type TypedReactElement[Result] = ReactElement

  object TypedReactElement:
    given fromWithAttrs[Result]: Conversion[WithAttrs[Result], TypedReactElement[Result]] =
      _()
    given fromKeyAddingStage[Result]: Conversion[TypedKeyAddingStage[Result], TypedReactElement[Result]] =
      KeyAddingStage.build(_)
    given toReactElement[Result]: Conversion[TypedReactElement[Result], ReactElement] =
      identity

  extension [Result](element: TypedReactElement[Result])
    def element: ReactElement = element
