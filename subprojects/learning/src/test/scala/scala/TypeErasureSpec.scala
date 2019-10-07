package learning.test.scala

import org.scalatest.FunSuite

class TypeErasureSpec extends FunSuite{
  case class Thing[T](value: T)

  test("Define problem with Type Erasure"){
    val seq = Seq(1,2,3)

    seq.isInstanceOf[Seq[Int]]
    seq.isInstanceOf[Seq[String]]
  }

  test("Show how type erasure work") {
    def processThing(thing: Thing[_]): String = {
      thing match {
        case Thing(value: Int) => "Thing of int"
        case Thing(value: Seq[Int]) => "Thing of Seq[int]"
        case _ => "Thing of something else"
      }
    }

    assert("Thing of int" == processThing(Thing(4)))
    assert("Thing of Seq[int]" == processThing(Thing(Seq(1, 2, 3))))
    assert("Thing of Seq[int]" == processThing(Thing(Seq("aaa", "bbb", "ccc"))), "TYPE ERASURE HERE")
  }

  test("Type erasure can be even worse, like:"){
    def processThing(thing: Thing[_]): String = {
      thing match {
        case Thing(value: Int) => "Thing of int"
        case Thing(value: Seq[Int]) => "Thing of Seq[int]" + value.sum // this will explode in case we pass Seq[String]
        case _ => "Thing of something else"
      }
    }
  }



}
