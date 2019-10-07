package learning.test.cats

import org.scalatest.FunSuite
import cats.Monoid
import cats.instances.string._

class CatsMonoidSpec extends FunSuite {

//  trait Monoid[A] {
//    def id: A // an identity element
//    def op(x: A, y: A): A // an associative operation
//  }

  test("monoid") {
    val result1 = Monoid[String].combine("hi", "there")
    val result2 = Monoid[String].combine("hi", "there")

    assert("hithere" === result1)
    assert("" === Monoid[String].empty)
  }
}
