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

// Move private demo.newtype.TypedReactElementDemo$(); to the bottom

//javap -c -p /Users/jason/src/big-little-types/demo/js/target/scala-3.2.1/test-classes/demo/newtype/TypedReactElementDemo$.class
//Compiled from "TypedReactElementDemo.scala"
//public final class demo.newtype.TypedReactElementDemo$ implements java.io.Serializable {
//  public static final demo.newtype.TypedReactElementDemo$ MODULE$;
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
//        : astore_0
//        : getstatic                      // Field scala/package$.MODULE$:Lscala/package$;
//        : invokevirtual                  // Method scala/package$.Seq:()Lscala/collection/immutable/Seq$;
//        : getstatic                      // Field scala/runtime/ScalaRunTime$.MODULE$:Lscala/runtime/ScalaRunTime$;
//        : iconst_1
//        : anewarray                      // class java/lang/Object
//        : dup
//        : iconst_0
//        : getstatic                      // Field TypedReactElement$.MODULE$:LTypedReactElement$;
//        : getstatic                      // Field Preamble$.MODULE$:LPreamble$;
//        : invokevirtual                  // Method Preamble$.tag:()Lscala/scalajs/js/Array;
//        : invokevirtual                  // Method TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Ljava/lang/Object;
//        : aastore
//        : invokevirtual                  // Method scala/runtime/ScalaRunTime$.genericWrapArray:(Ljava/lang/Object;)Lscala/collection/immutable/ArraySeq;
//        : invokevirtual                  // Method scala/collection/immutable/Seq$.apply:(Lscala/collection/immutable/Seq;)Lscala/collection/SeqOps;
//        : checkcast                      // class scala/collection/immutable/Seq
//        : astore_1
//        : aload_1
//        : invokeinterface ,  1           // InterfaceMethod scala/collection/immutable/Seq.head:()Ljava/lang/Object;
//        : astore_2
//        : return
//
//  private java.lang.Object writeReplace();
//    Code:
//        : new                            // class scala/runtime/ModuleSerializationProxy
//        : dup
//        : ldc                            // class TypedReactElementDemo$
//        : invokespecial                  // Method scala/runtime/ModuleSerializationProxy."<init>":(Ljava/lang/Class;)V
//        : areturn
//
//  private demo.newtype.TypedReactElementDemo$();
//    Code:
//        : aload_0
//        : invokespecial                  // Method java/lang/Object."<init>":()V
//        : return
//}
object TypedReactElementDemo:

  private val item: TypedReactElement[li.tag.type] = tag
  private val items: Seq[TypedReactElement[li.tag.type]] = Seq(tag)
  private val first: TypedReactElement[li.tag.type] = items.head
