package demo.newtype

object Stable {

  // This stuff is a bit crazy. See https://github.com/scala-js/scala-js/issues/4778 for the rationale.

  type Base = {
    type __Stable
  }

  sealed trait Tag extends Any

  type Type[A] <: Base with A with Tag

  def byReference[A](a: A): Stable[A] =
    a.asInstanceOf[Stable[A]]

}
