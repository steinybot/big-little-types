package demo.newtype

import slinky.web.html.li
import Preamble._

//javap -c -p /Users/jason/src/big-little-types/demo/js/target/scala-3.2.1/test-classes/demo/newtype/TypedReactElementDemo$.class ~/src/big-little-types
//Compiled from "TypedReactElementDemo.scala"
//public final class demo.newtype.TypedReactElementDemo$ implements java.io.Serializable {
//  public static final demo.newtype.TypedReactElementDemo$ MODULE$;
//
//  private static final java.lang.Object item;
//
//  public static {};
//    Code:
//       0: new           #2                  // class demo/newtype/TypedReactElementDemo$
//       3: dup
//       4: invokespecial #18                 // Method "<init>":()V
//       7: putstatic     #20                 // Field MODULE$:Ldemo/newtype/TypedReactElementDemo$;
//      10: getstatic     #25                 // Field demo/newtype/TypedReactElement$.MODULE$:Ldemo/newtype/TypedReactElement$;
//      13: getstatic     #30                 // Field demo/newtype/Preamble$.MODULE$:Ldemo/newtype/Preamble$;
//      16: invokevirtual #34                 // Method demo/newtype/Preamble$.tag:()Lscala/scalajs/js/Array;
//      19: invokevirtual #38                 // Method demo/newtype/TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Ljava/lang/Object;
//      22: putstatic     #40                 // Field item:Ljava/lang/Object;
//      25: return
//
//  private java.lang.Object writeReplace();
//    Code:
//       0: new           #44                 // class scala/runtime/ModuleSerializationProxy
//       3: dup
//       4: ldc           #2                  // class demo/newtype/TypedReactElementDemo$
//       6: invokespecial #47                 // Method scala/runtime/ModuleSerializationProxy."<init>":(Ljava/lang/Class;)V
//       9: areturn
//
//  public java.lang.Object item();
//    Code:
//       0: getstatic     #40                 // Field item:Ljava/lang/Object;
//       3: areturn
//
//  private demo.newtype.TypedReactElementDemo$();
//    Code:
//       0: aload_0
//       1: invokespecial #15                 // Method java/lang/Object."<init>":()V
//       4: return
//}
object TypedReactElementDemo:

  val item: TypedReactElement[li.tag.type] = tag
