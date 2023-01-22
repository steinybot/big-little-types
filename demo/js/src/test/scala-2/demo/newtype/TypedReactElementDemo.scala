package demo.newtype

import slinky.web.html.li
import Preamble._

//Regex replace:
//[\d ]\d(: .*?)(#\d[\d ])(.*)
//  $1$3

//Regex replace:
//[\d ]\d(: .*?)(.*)
//  $1$2

//Regex replace:
//demo/newtype/
//

//javap -c -p /Users/jason/src/big-little-types/demo/js/target/scala-2.13/test-classes/demo/newtype/TypedReactElementDemo$.class
//Compiled from "TypedReactElementDemo.scala"
//public final class demo.newtype.TypedReactElementDemo$ {
//  public static final demo.newtype.TypedReactElementDemo$ MODULE$;
//
//  private static final java.lang.Object item;
//
//  private static final scala.collection.immutable.Seq<java.lang.Object> items;
//
//  private static final java.lang.Object first;
//
//  public static {};
//    Code:
//        : new                            // class TypedReactElementDemo$
//        : dup
//        : invokespecial                  // Method "<init>":()V
//        : putstatic                      // Field MODULE$:LTypedReactElementDemo$;
//        : getstatic                      // Field TypedReactElement$.MODULE$:LTypedReactElement$;
//        : getstatic                      // Field Preamble$.MODULE$:LPreamble$;
//        : invokevirtual                  // Method Preamble$.tag:()Lscala/scalajs/js/Array;
//        : invokevirtual                  // Method TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Ljava/lang/Object;
//        : putstatic                      // Field item:Ljava/lang/Object;
//        : new                            // class scala/collection/immutable/$colon$colon
//        : dup
//        : getstatic                      // Field TypedReactElement$.MODULE$:LTypedReactElement$;
//        : getstatic                      // Field Preamble$.MODULE$:LPreamble$;
//        : invokevirtual                  // Method Preamble$.tag:()Lscala/scalajs/js/Array;
//        : invokevirtual                  // Method TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Ljava/lang/Object;
//        : getstatic                      // Field scala/collection/immutable/Nil$.MODULE$:Lscala/collection/immutable/Nil$;
//        : invokespecial                  // Method scala/collection/immutable/$colon$colon."<init>":(Ljava/lang/Object;Lscala/collection/immutable/List;)V
//        : putstatic                      // Field items:Lscala/collection/immutable/Seq;
//        : getstatic                      // Field MODULE$:LTypedReactElementDemo$;
//        : invokespecial                  // Method items:()Lscala/collection/immutable/Seq;
//        : invokeinterface ,  1           // InterfaceMethod scala/collection/immutable/Seq.head:()Ljava/lang/Object;
//        : putstatic                      // Field first:Ljava/lang/Object;
//        : return
//
//  private java.lang.Object item();
//    Code:
//        : getstatic                      // Field item:Ljava/lang/Object;
//        : areturn
//
//  private scala.collection.immutable.Seq<java.lang.Object> items();
//    Code:
//        : getstatic                      // Field items:Lscala/collection/immutable/Seq;
//        : areturn
//
//  private java.lang.Object first();
//    Code:
//        : getstatic                      // Field first:Ljava/lang/Object;
//        : areturn
//
//  private demo.newtype.TypedReactElementDemo$();
//    Code:
//        : aload_0
//        : invokespecial                  // Method java/lang/Object."<init>":()V
//        : return
//}
object TypedReactElementDemo {

  private val item: TypedReactElement[li.tag.type] = tag
  private val items: Seq[TypedReactElement[li.tag.type]] = Seq(tag)
  private val first: TypedReactElement[li.tag.type] = items.head
}
