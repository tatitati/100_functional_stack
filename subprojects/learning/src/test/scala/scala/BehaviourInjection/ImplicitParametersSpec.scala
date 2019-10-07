package learning.test.scala.BehaviourInjection

import org.scalatest.FunSuite

class ImplicitParametersSpec extends FunSuite {

  test("Simple implicit parameters") {
    implicit val friend = "Antonio"

    def sayHello(implicit name: String): String = s"Hello $name"

    assert("Hello Antonio" === sayHello)
  }

  test("With generics, among all the implicits that can be injected, it injects the once matching the TYPE") {
      case class User(name: String, age: Int)

      trait InfoPrinter[T]{
        def toInfo(value: T): String
      }

      implicit val stringPrinter = new InfoPrinter[String] {
        override def toInfo(value: String): String = s"[__STRING__] $value"
      }

      implicit val userPrinter = new InfoPrinter[User] {
        override def toInfo(value: User): String = s"[__USER__] ${value.name}, ${value.age}"
      }

      def printInfo[A](value: A)(implicit printer: InfoPrinter[A]): String = {
        printer.toInfo(value)
      }

      assert("[__STRING__] hello" === printInfo("hello"))
      assert("[__USER__] Raul, 23" === printInfo(User("Raul", 23)))
  }
}
