package slides.l

import org.scalajs.dom.document
import org.scalatest.Assertion
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.l.RenderLimitHook.useRenderLimit
import slinky.core.FunctionalComponent
import slinky.core.facade.{Hooks, SetStateHookCallback}
import slinky.web.ReactDOM
import slinky.web.html.{readOnly, textarea, value}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Promise}
import scala.scalajs.concurrent.QueueExecutionContext
import scala.scalajs.js.timers.{clearTimeout, setTimeout}
import scala.util.Try

object SafeHooks5 {

  type Stable[A] = Stable.Type[A]

  object Stable {

    type Base = {
      type __Stable
    }

    sealed trait Tag extends Any

    type Type[A] <: Base with A with Tag

    def byReference[A](a: A): Stable[A] =
      a.asInstanceOf[Stable[A]]
  }

  def useEffect(thunk: () => Unit, dependencies: Iterable[Stable[_]]): Unit =
    Hooks.useEffect(thunk, dependencies)

  def useEffectCancellable(thunk: () => () => Unit, dependencies: Iterable[Stable[_]]): Unit =
    Hooks.useEffect(thunk, dependencies)

  def useState[A](initial: A): (Stable[A], SetStateHookCallback[A]) = {
    val (state, setState) = Hooks.useState(initial)
    (Stable.byReference(state), setState)
  }

  def useMemo[A](memoValue: () => A, dependencies: Iterable[Stable[_]]): Stable[A] = {
    val value = Hooks.useMemo[A](memoValue, dependencies)
    Stable.byReference(value)
  }

  case class Params(text: String, count: Int)

  val component = FunctionalComponent[Unit] { _ =>
    // TODO: Remove this from the slides
    useRenderLimit(10)

    val (messages, setMessages) = useState(Vector.empty[String])
    val (params, setParams) = useState(Params("", 10))

    val paramsWithDefaults = useMemo(() => params.copy(
      text = if (params.text.nonEmpty) params.text else "foo"
    ), Seq(params))

    useEffect(() => {
      setMessages(_ :+ s"Loading data with params: $paramsWithDefaults")
      val handle = setTimeout(1.seconds)(setMessages(_ :+ "Data loaded."))
      () => clearTimeout(handle)
    }, Seq(paramsWithDefaults))

    textarea(readOnly := true, value := messages.mkString("\n"))
  }
}

class SafeHooks5 extends AsyncFlatSpec {

  import SafeHooks5._

  override implicit val executionContext: ExecutionContext = QueueExecutionContext.timeouts()

  behavior of "SafeHooks5"

  it should "fail to compile if the dependency is not stable" in {
    """
    val (messages, setMessages) = useState(Vector.empty[String])
    val (params, setParams) = useState(Params("", 10))

    val paramsWithDefaults = params.copy(
      text = if (params.text.nonEmpty) params.text else "foo"
    )

    useEffect(() => {
      setMessages(_ :+ s"Loading data with params: $paramsWithDefaults")
      val handle = setTimeout(1.seconds)(setMessages(_ :+ "Data loaded."))
      () => clearTimeout(handle)
    }, Seq(paramsWithDefaults))
    """ shouldNot typeCheck
  }

  it should "do something" in {
    val element = component(())
    val container = document.createElement("div")
    ReactDOM.render(element, container)
    val promise = Promise[Assertion]()
    setTimeout(3.seconds) {
      promise.complete {
        Try {
          container.innerHTML shouldBe
            """<textarea readonly="">Loading data with params: Params(foo,10)
              |Data loaded.</textarea>""".stripMargin
        }
      }
    }
    promise.future
  }
}
