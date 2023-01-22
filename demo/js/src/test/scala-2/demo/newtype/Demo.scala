package demo.newtype

import demo.newtype.Preamble._
import slinky.core.WithAttrs
import slinky.web.html.li

object Preamble {

  def tag: WithAttrs[li.tag.type] = li("Apple")

  final case class Props(name: String)

  val typedComponent: TypedFunctionalComponent[Props, li.tag.type] =
    TypedFunctionalComponent[Props, li.tag.type] { props =>
      li(props.name)
    }

  val typedReactElement: TypedReactElement[li.tag.type] = tag

  def typedKeyAddingStage: TypedKeyAddingStage[li.tag.type] = typedComponent(Props("Apple"))
}

// javap -c -p /Users/jason/src/big-little-types/demo/js/target/scala-2.13/test-classes/demo/newtype/Demo$.class
//Compiled from "Demo.scala"
//public final class demo.newtype.Demo$ {
//  public static final demo.newtype.Demo$ MODULE$;
//
//  private static final java.lang.Object item;
//
//  public static {};
//    Code:
//       0: new           #2                  // class demo/newtype/Demo$
//       3: dup
//       4: invokespecial #14                 // Method "<init>":()V
//       7: putstatic     #16                 // Field MODULE$:Ldemo/newtype/Demo$;
//      10: getstatic     #21                 // Field demo/newtype/TypedReactElement$.MODULE$:Ldemo/newtype/TypedReactElement$;
//      13: getstatic     #26                 // Field demo/newtype/Preamble$.MODULE$:Ldemo/newtype/Preamble$;
//      16: invokevirtual #30                 // Method demo/newtype/Preamble$.tag:()Lscala/scalajs/js/Array;
//      19: invokevirtual #34                 // Method demo/newtype/TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Ljava/lang/Object;
//      22: putstatic     #36                 // Field item:Ljava/lang/Object;
//      25: return
//
//  public java.lang.Object item();
//    Code:
//       0: getstatic     #36                 // Field item:Ljava/lang/Object;
//       3: areturn
//
//  private demo.newtype.Demo$();
//    Code:
//       0: aload_0
//       1: invokespecial #39                 // Method java/lang/Object."<init>":()V
//       4: return
//}
//
//Process finished with exit code 0
object Demo {

  val item: TypedReactElement[li.tag.type] = tag
}
