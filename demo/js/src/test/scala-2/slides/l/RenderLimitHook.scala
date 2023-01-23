package slides.l

import slinky.core.facade.Hooks._

import scala.scalajs.js.JavaScriptException

object RenderLimitHook {

  def useRenderLimit(limit: Int): Unit = {
    val count = useRef(0)
    if (count.current >= limit) {
      throw JavaScriptException(s"Reached render limit of $limit")
    }
    useEffect(() => {
      count.current += 1
    })
  }
}
