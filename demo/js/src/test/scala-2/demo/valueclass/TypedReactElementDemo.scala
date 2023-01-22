package demo.valueclass

import demo.valueclass.Preamble._
import slinky.web.html.li

//javap -c -p /Users/jason/src/big-little-types/demo/js/target/scala-2.13/test-classes/demo/valueclass/TypedReactElementDemo$.class
//Compiled from "TypedReactElementDemo.scala"
//public final class demo.valueclass.TypedReactElementDemo$ {
//  public static final demo.valueclass.TypedReactElementDemo$ MODULE$;
//
//  private static final slinky.core.facade.ReactElement item;
//
//  public static {};
//    Code:
//       0: new           #2                  // class demo/valueclass/TypedReactElementDemo$
//       3: dup
//       4: invokespecial #14                 // Method "<init>":()V
//       7: putstatic     #16                 // Field MODULE$:Ldemo/valueclass/TypedReactElementDemo$;
//      10: getstatic     #21                 // Field demo/valueclass/TypedReactElement$.MODULE$:Ldemo/valueclass/TypedReactElement$;
//      13: getstatic     #26                 // Field demo/valueclass/Preamble$.MODULE$:Ldemo/valueclass/Preamble$;
//      16: invokevirtual #30                 // Method demo/valueclass/Preamble$.tag:()Lscala/scalajs/js/Array;
//      19: invokevirtual #34                 // Method demo/valueclass/TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Lslinky/core/facade/ReactElement;
//      22: putstatic     #36                 // Field item:Lslinky/core/facade/ReactElement;
//      25: return
//
//  public slinky.core.facade.ReactElement item();
//    Code:
//       0: getstatic     #36                 // Field item:Lslinky/core/facade/ReactElement;
//       3: areturn
//
//  private demo.valueclass.TypedReactElementDemo$();
//    Code:
//       0: aload_0
//       1: invokespecial #39                 // Method java/lang/Object."<init>":()V
//       4: return
//}
object TypedReactElementDemo {

  val item: TypedReactElement[li.tag.type] = tag
}
