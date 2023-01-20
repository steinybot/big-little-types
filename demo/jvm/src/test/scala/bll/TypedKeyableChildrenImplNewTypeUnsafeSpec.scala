package bll

import bll.TypedKeyableChildrenImplNewTypeUnsafeSpec._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._

// https://failex.blogspot.com/2017/04/the-high-cost-of-anyval-subclasses.html

object TypedKeyableChildrenImplNewTypeUnsafeSpec {

  trait ReactElement
  final class KeyAddingStage(private val args: Array[Any]) extends AnyVal

  trait Tag[Result]

  type TypedKeyAddingStage[Result] = KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage {
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]
  }
}

class TypedKeyableChildrenImplNewTypeUnsafeSpec extends AnyFlatSpec {

  behavior of "Typed keyable children"

  it should "fail with a ..." in {
    def RedList(children: TypedKeyAddingStage[String]*): ReactElement =
      new ReactElement {}

    object QuantifiedListItem {
      def apply(amount: Int)(children: String): KeyAddingStage =
        new KeyAddingStage(Array.empty)
    }

    RedList( //
      TypedKeyAddingStage.unsafe(QuantifiedListItem(1)("Apple")),
      TypedKeyAddingStage.unsafe(QuantifiedListItem(3)("Bananas")),
      TypedKeyAddingStage.unsafe(QuantifiedListItem(5)("Cherries"))
    )
  }
}