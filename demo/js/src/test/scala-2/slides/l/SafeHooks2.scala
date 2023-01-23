package slides.l

import org.scalajs.dom.document
import org.scalatest.Assertion
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.l.RenderLimitHook.useRenderLimit
import slides.l.SafeHooks2.component
import slinky.core.FunctionalComponent
import slinky.core.facade.Hooks._
import slinky.web.ReactDOM
import slinky.web.html.{readOnly, textarea, value}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Promise}
import scala.scalajs.concurrent.QueueExecutionContext
import scala.scalajs.js.timers.{clearTimeout, setTimeout}
import scala.util.Try

object SafeHooks2 {

  case class Params(text: String, count: Int)

  val component = FunctionalComponent[Unit] { _ =>
    // TODO: Remove this from the slides
    useRenderLimit(10)

    val (messages, setMessages) = useState(Vector.empty[String])
    val (params, setParams) = useState(Params("", 10))

    useEffect(() => {
      setMessages(_ :+ s"Loading data with params: $params")
      val handle = setTimeout(1.seconds)(setMessages(_ :+ "Data loaded."))
      () => clearTimeout(handle)
    }, Seq(params))

    textarea(readOnly := true, value := messages.mkString("\n"))
  }
}

class SafeHooks2 extends AsyncFlatSpec {

  override implicit val executionContext: ExecutionContext = QueueExecutionContext.timeouts()

  behavior of "SafeHooks2"

  it should "do something" in {
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
