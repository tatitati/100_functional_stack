package learning.test.scala

import org.scalatest.FunSuite

class GenericSpec extends FunSuite {

    test("generic with _") {
      def func1(a: List[_]): Int = {
        a.size
      }

      assert(3 === func1(List("a", "b", "c")))
    }

  test("F as generic ") {
    case class MyOptions[F](name: Option[F], surname: Option[F])

    MyOptions(
      name = Option("a name"),
      surname = Option("a surname")
    )
  }
}
