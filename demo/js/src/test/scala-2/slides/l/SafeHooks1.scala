package slides.l

import org.scalajs.dom.document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import slides.l.RenderLimitHook.useRenderLimit
import slides.l.SafeHooks1.component
import slinky.core.FunctionalComponent
import slinky.core.facade.Hooks._
import slinky.web.ReactDOM
import slinky.web.html.{readOnly, textarea, value}

import scala.concurrent.duration._
import scala.scalajs.js.timers.{clearTimeout, setTimeout}

object SafeHooks1 {

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
    })

    textarea(readOnly := true, value := messages.mkString("\n"))

    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
    //Warning: Maximum update depth exceeded. This can happen when a component calls setState inside useEffect, but useEffect either doesn't have a dependency array, or one of the dependencies changes on every render.
  }
}

class SafeHooks1 extends AnyFlatSpec {

  behavior of "SafeHooks1"

  it should "do something" in pendingUntilFixed {
    ProcessHandlers.installUnhandledRejection()
    val element = ErrorBoundary(component(()))
    val container = document.createElement("div")
    ReactDOM.render(element, container)
    container.innerHTML shouldBe "TODO"
  }
}
