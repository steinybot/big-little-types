package slides.a

import slinky.core.{FunctionalComponent, KeyAddingStage, WithAttrs}
import slinky.core.facade.ReactElement
import slinky.web.html.li

object Slinky1 {

  val tag: WithAttrs[li.tag.type] = li("Apple")

  val renderedTag: ReactElement = tag

  val component: FunctionalComponent[Props] =
    FunctionalComponent[Props] { props =>
      li(s"${props.children} * ${props.amount}")
    }

  def fn(amount: Int)(children: String) =
    li(s"$children * $amount")

  val componentWithProps: KeyAddingStage = component(Props(5, "Cherries"))

  val componentWithKey: ReactElement = componentWithProps.withKey("cherries")

  val renderedComponent: ReactElement = componentWithProps

}
