package demo.newtype

import slinky.web.html.li
import Preamble._

//javap -c -p /Users/jason/src/big-little-types/demo/js/target/scala-2.13/test-classes/demo/newtype/TypedReactElementDemo$.class
//Compiled from "TypedReactElementDemo.scala"
//public final class demo.newtype.TypedReactElementDemo$ {
//  public static final demo.newtype.TypedReactElementDemo$ MODULE$;
//
//  private static final java.lang.Object item;
//
//  public static {};
//    Code:
//       0: new           #2                  // class demo/newtype/TypedReactElementDemo$
//       3: dup
//       4: invokespecial #14                 // Method "<init>":()V
//       7: putstatic     #16                 // Field MODULE$:Ldemo/newtype/TypedReactElementDemo$;
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
//  private demo.newtype.TypedReactElementDemo$();
//    Code:
//       0: aload_0
//       1: invokespecial #39                 // Method java/lang/Object."<init>":()V
//       4: return
//}
object TypedReactElementDemo {

  val item: TypedReactElement[li.tag.type] = tag
}
