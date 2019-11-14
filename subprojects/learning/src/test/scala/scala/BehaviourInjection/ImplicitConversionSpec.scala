package learning.test.scala.BehaviourInjection

import org.scalatest.FunSuite

class ImplicitConversionSpec extends FunSuite {

  case class ShortUser(name: String)
  case class FullUser(name: String, age: Int, gender: String)

  val full = FullUser(name="francisco", age=23, gender="male")

  test("CONVERTING ARGUMENTS: displayName() argument is converted behind the scenes from Full to Short") {
    implicit def full2Short(user: FullUser): ShortUser = {
      ShortUser(user.name)
    }

    def displayName(short: ShortUser): String = short.name

    assert("francisco" == displayName(full))
  }

  test("INJECTING METHODS:  .greet() by converting the caller from String to Person") {
    case class Person(name: String) {
      def greet(): String = s"Hello person: $name"
    }

    implicit def liftStringToperson(str: String): Person = Person(str)

    assert("Hello person: Maria" === "Maria".greet())
  }

  test("INJECTING METHODS-SHORTCUT: To avoid the intermediate converter we can use the shortcut") {
    implicit class StringToperson(str: String) {
      def greet(): String = s"Hello $str"
    }

    assert("Hello Maria" === "Maria".greet())
  }
}
