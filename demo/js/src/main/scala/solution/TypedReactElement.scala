package solution

import slinky.core.WithAttrs
import slinky.core.facade.ReactElement

object TypedReactElement {

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __TypedReactElement__newtype
  }

  sealed trait Tag[+Props] extends Any

  type Type[+Result] <: Base with ReactElement with Tag[Result]

//  implicit def apply[Result](result: Result)(implicit renderer: Renderer[Result]): TypedReactElement[Result] =
//    renderer.render(result).asInstanceOf[TypedReactElement[Result]]

  implicit def fromWithAttrs[Result](result: WithAttrs[Result]): TypedReactElement[Result] =
    result().asInstanceOf[TypedReactElement[Result]]
//
//  implicit def fromResult[Result](result: TypedKeyAddingStage[Result]): TypedReactElement[Result] =
//    KeyAddingStage.build(result).asInstanceOf[TypedReactElement[Result]]

}
