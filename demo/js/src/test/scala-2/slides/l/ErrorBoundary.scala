package slides.l

import org.scalajs.dom.console
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.{ErrorBoundaryInfo, Fragment, ReactElement}

import scala.scalajs.js
import scala.scalajs.js.special.debugger

@react
class ErrorBoundary extends Component {

  case class Props(children: ReactElement*)
  case class State(error: Option[js.Error])

  override def initialState: State = State(None)

  override def componentDidCatch(error: js.Error, info: ErrorBoundaryInfo): Unit = {
    console.error(error)
    setState(_.copy(error = Some(error)))
  }

  override def render(): ReactElement = {
    console.log(s"Rendering: $state")
    state.error.fold[ReactElement] {
        Fragment(props.children: _*)
    } (_.message)
  }
}

object ErrorBoundary {
  override val getDerivedStateFromError = (error: js.Error) => State(Some(error))
}