package learning.test.scala

import org.scalatest.FunSuite

class FilterSpec extends FunSuite {
  test("Filter") {
    def isPar(a: Int): Boolean = {
      a % 2 match {
        case 0 => true
        case _ => false
      }
    }

    val l = List(1,2,3,4,5)

    val result1 = l.filter( x => isPar(x) )
    val result2 = l.filter( x => x % 2 == 0 )

    assert(List(2, 4) === result1)
    assert(List(2, 4) === result2)
  }
}
