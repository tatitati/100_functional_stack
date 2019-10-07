package learning.test.cats

import org.scalatest.FunSuite
import cats.{Semigroup, Semigroupal}
import cats.implicits._

class CatSemigroupSpec extends FunSuite {

  // Trait Semigroup[A] {
  //    def combine(x: A, y: A): A
  // }

  test("semigroup") {
    val result1 = Semigroup[String].combine("hi", "there")
    val result2 = Semigroup[Option[String]].combine(Some("hi"), Some("there"))

    assert("hithere" == result1)
    assert(Some("hithere") == result2)
  }

  test("semigroups are not semigroupal:") {
    val result1 = Semigroup[Option[String]].combine(Some("hi"), Some("there"))
    val result2 = Semigroupal[Option].product(Some("hi"), Some("there"))

    assert(Some("hithere") == result1)
    assert(Some(("hi", "there")) === result2)
  }
}
