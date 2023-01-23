package slides.l

import org.scalajs.dom.console

import scala.scalajs.js
import scala.scalajs.js.{JavaScriptException, UndefOr, |}
import scala.scalajs.js.annotation.JSImport

// FIXME: How can we include this in tests?
@js.native
@JSImport("process", JSImport.Namespace)
object Process extends js.Object {

  def on(event: "unhandledRejection", listener: UnhandledRejectionListener): Process.type = js.native

  type UnhandledRejectionListener = js.Function2[UndefOr[js.Object] | Null, js.Promise[Any], Unit]
}

object ProcessHandlers {

  private var unhandledRejectionInstalled = false

  // jsdom causes Node.js to exit with exit code 1 whenever there is an unhandledRejection.
  // This is sketchy but it stops that from happening.
  def installUnhandledRejection(): Unit = {
    if (!unhandledRejectionInstalled) {
      console.log("Installing unhandledRejection handler.")
      Process.on("unhandledRejection", (reason, _) => {
        console.error(reason)
      })
    }
    unhandledRejectionInstalled = true
  }
}
