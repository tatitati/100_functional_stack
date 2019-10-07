package learning.test.scala

import org.scalatest.FunSuite

class MapSpec extends FunSuite {

  // About [A]...[A] defines the content of our List, is defined like:
  //
  //      trait List[A] {
  //        def map[B](f: A => B): List[B]
  //        def flatMap[B](f: A => List[B]): List[B]
  //      }

  test("map") {
    assert(List(1,2,3,4,5)                  === List(1,2,3,4,5).map( x => x))
    assert(List(2, 4, 6, 8, 10)             === List(1,2,3,4,5).map( x => x * 2 ))
    assert(List("1", "2", "3", "4", "5")    === List(1,2,3,4,5).map( x => x.toString ))
    assert(List(2, 5)                       === List("hi", "there").map( _.length ))
  }

  test("Map know how to extract the value contained in a wrapper") {
    val a = Some(22)

    a.map{ aVal =>
      assert(22 === aVal)
    }
  }

  test("how to do a + b") {
    val a = Some(2)
    val b = Some(3)

    val sum =
      a.map{ aVal =>
        b.map { bVal =>
          aVal + bVal
      }
    }

    assert(Some(Some(5)) === sum)
  }
}
