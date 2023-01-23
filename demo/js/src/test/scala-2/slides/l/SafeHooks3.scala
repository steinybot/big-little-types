package slides.l

import org.scalajs.dom.document
import org.scalatest.Assertion
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.l.RenderLimitHook.useRenderLimit
import slides.l.SafeHooks3.component
import slinky.core.FunctionalComponent
import slinky.core.facade.Hooks._
import slinky.web.ReactDOM
import slinky.web.html.{readOnly, textarea, value}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Promise}
import scala.scalajs.concurrent.QueueExecutionContext
import scala.scalajs.js.timers.{clearTimeout, setTimeout}
import scala.util.Try

object SafeHooks3 {

  case class Params(text: String, count: Int)

  val component = FunctionalComponent[Unit] { _ =>
    // TODO: Remove this from the slides
    useRenderLimit(10)

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

    textarea(readOnly := true, value := messages.mkString("\n"))

    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //    in SafeHooks1
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //    in SafeHooks1
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //    in SafeHooks1
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //    in SafeHooks1
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //    in SafeHooks1
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //    in SafeHooks1
  }
}

class SafeHooks3 extends AsyncFlatSpec {

  override implicit val executionContext: ExecutionContext = QueueExecutionContext.timeouts()

  behavior of "SafeHooks3"

  it should "do something" in pendingUntilFixed {
    ProcessHandlers.installUnhandledRejection()
    val element = component(())
    val container = document.createElement("div")
    ReactDOM.render(element, container)
    val promise = Promise[Assertion]()
    setTimeout(3.seconds) {
      promise.complete {
        Try {
          container.innerHTML shouldBe
            """<textarea readonly="">Loading data with params: Params(,10)
              |Data loaded.</textarea>""".stripMargin
        }
      }
    }
    promise.future
  }
}
