package learning.test.cats

import cats._
import org.scalatest.FunSuite
import cats.instances.string._
import cats.instances.int._

class CatShowSpec extends FunSuite {
  test("show") {
    val showInt: Show[Int] = Show.apply[Int]
    val showString: Show[String] = Show.apply[String]

    assert("123" === showInt.show(123))
    assert("whatever" === showString.show("whatever"))
  }

  test("show even easier") {
    import cats.syntax.show._

    assert("123" === 123.show)
    assert("whatever" === "whatever".show)
  }
}
