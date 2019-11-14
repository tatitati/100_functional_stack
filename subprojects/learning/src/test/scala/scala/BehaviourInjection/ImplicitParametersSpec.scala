package learning.test.scala.BehaviourInjection

import org.scalatest.FunSuite

class ImplicitParametersSpec extends FunSuite {

  test("Implicit variables don't need to be passed to the function") {
    implicit val friend = "Antonio"
    def sayHello(implicit name: String): String = s"Hello $name"

    assert("Hello Antonio" === sayHello)
  }

  test("The correct implicit variable can be injected") {
      case class User(name: String, age: Int)

      // type class
      trait InfoPrinter[T]{
        def toInfo(value: T): String
      }

      // instances
      implicit val stringPrinter = new InfoPrinter[String] {
        override def toInfo(value: String): String = s"StringPrinter: $value"
      }

      implicit val userPrinter = new InfoPrinter[User] {
        override def toInfo(value: User): String = s"UserPrinter: ${value.name}, ${value.age}"
      }

      // use case
      def printInfo[A](value: A)(implicit printer: InfoPrinter[A]): String = {
        printer.toInfo(value)
      }

      assert("StringPrinter: hello" === printInfo("hello"))
      assert("UserPrinter: Raul, 23" === printInfo(User("Raul", 23)))
  }
}
