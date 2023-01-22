package slides.f

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slinky.core.KeyAddingStage

class TypedKeyAddingStage1 extends AnyFlatSpec:

  behavior of "TypedKeyAddingStage1"

  it should "fail to compile when wrapping another value class" in {
    """
    class TypedKeyAddingStage[Result](val stage: KeyAddingStage) extends AnyVal
    """ shouldNot compile
  }
