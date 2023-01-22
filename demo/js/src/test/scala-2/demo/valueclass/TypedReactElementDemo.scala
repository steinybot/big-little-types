package demo.valueclass

import slinky.web.html.li
import Preamble._

//Regex replace:
//[\d ]\d(: .*?)(#\d[\d ])(.*)
//  $1$3

//Regex replace:
//[\d ]\d(: .*?)(.*)
//  $1$2

//Regex replace:
//demo/valueclass/
//

//javap -c -p /Users/jason/src/big-little-types/demo/js/target/scala-2.13/test-classes/demo/valueclass/TypedReactElementDemo$.class
//Compiled from "TypedReactElementDemo.scala"
//public final class demo.valueclass.TypedReactElementDemo$ {
//  public static final demo.valueclass.TypedReactElementDemo$ MODULE$;
//
//  private static final slinky.core.facade.ReactElement item;
//
//  private static final scala.collection.immutable.Seq<demo.valueclass.TypedReactElement<slinky.web.html.li$tag$>> items;
//
//  private static final slinky.core.facade.ReactElement first;
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
//        : invokevirtual                  // Method TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Lslinky/core/facade/ReactElement;
//        : putstatic                      // Field item:Lslinky/core/facade/ReactElement;
//        : getstatic                      // Field scala/package$.MODULE$:Lscala/package$;
//        : invokevirtual                  // Method scala/package$.Seq:()Lscala/collection/immutable/Seq$;
//        : getstatic                      // Field scala/runtime/ScalaRunTime$.MODULE$:Lscala/runtime/ScalaRunTime$;
//        : iconst_1
//        : anewarray                      // class TypedReactElement
//        : dup
//        : iconst_0
//        : new                            // class TypedReactElement
//        : dup
//        : getstatic                      // Field TypedReactElement$.MODULE$:LTypedReactElement$;
//        : getstatic                      // Field Preamble$.MODULE$:LPreamble$;
//        : invokevirtual                  // Method Preamble$.tag:()Lscala/scalajs/js/Array;
//        : invokevirtual                  // Method TypedReactElement$.fromWithAttrs:(Lscala/scalajs/js/Array;)Lslinky/core/facade/ReactElement;
//        : invokespecial                  // Method TypedReactElement."<init>":(Lslinky/core/facade/ReactElement;)V
//        : aastore
//        : invokevirtual                  // Method scala/runtime/ScalaRunTime$.genericWrapArray:(Ljava/lang/Object;)Lscala/collection/immutable/ArraySeq;
//        : invokevirtual                  // Method scala/collection/immutable/Seq$.apply:(Lscala/collection/immutable/Seq;)Lscala/collection/SeqOps;
//        : checkcast                      // class scala/collection/immutable/Seq
//        : putstatic                      // Field items:Lscala/collection/immutable/Seq;
//        : getstatic                      // Field MODULE$:LTypedReactElementDemo$;
//        : invokespecial                  // Method items:()Lscala/collection/immutable/Seq;
//        : invokeinterface ,  1           // InterfaceMethod scala/collection/immutable/Seq.head:()Ljava/lang/Object;
//        : checkcast                      // class TypedReactElement
//        : invokevirtual                  // Method TypedReactElement.element:()Lslinky/core/facade/ReactElement;
//        : putstatic                      // Field first:Lslinky/core/facade/ReactElement;
//        : return
//
//  private slinky.core.facade.ReactElement item();
//    Code:
//        : getstatic                      // Field item:Lslinky/core/facade/ReactElement;
//        : areturn
//
//  private scala.collection.immutable.Seq<demo.valueclass.TypedReactElement<slinky.web.html.li$tag$>> items();
//    Code:
//        : getstatic                      // Field items:Lscala/collection/immutable/Seq;
//        : areturn
//
//  private slinky.core.facade.ReactElement first();
//    Code:
//        : getstatic                      // Field first:Lslinky/core/facade/ReactElement;
//        : areturn
//
//  private demo.valueclass.TypedReactElementDemo$();
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
