package slides.g

import org.scalatest.flatspec.AnyFlatSpec
import slides.e.KeyAddingStage2.QuantifiedListItem
import slinky.core.KeyAddingStage
import slinky.web.html.li
import org.scalatest.matchers.should.Matchers._

object TypeAlias1 {

  trait Tag[Result]

  type TypedKeyAddingStage[Result] = KeyAddingStage with Tag[Result]

  object TypedKeyAddingStage {
    def unsafe[Result](stage: KeyAddingStage) =
      stage.asInstanceOf[TypedKeyAddingStage[Result]]
  }

  val cherries: KeyAddingStage = QuantifiedListItem(5)("Cherries")

  val typedCherries: TypedKeyAddingStage[li.tag.type] =
    TypedKeyAddingStage.unsafe(cherries)

  val fruit: Seq[TypedKeyAddingStage[li.tag.type]] = Seq(typedCherries)
}

class TypeAlias1 extends AnyFlatSpec {

  import TypeAlias1._

  behavior of "Type alias"

  // Well it shouldn't but it does...
  it should "throw a ClassCastException" in {
    val caught = intercept[Throwable] {
      val firstFruit: TypedKeyAddingStage[li.tag.type] = fruit.head
    }
    caught.getCause shouldBe a[ClassCastException]
  }
}
