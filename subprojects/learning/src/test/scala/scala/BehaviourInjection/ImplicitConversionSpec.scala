package learning.test.scala.BehaviourInjection

import org.scalatest.FunSuite

class ImplicitConversionSpec extends FunSuite {

  case class FullUser(name: String, age: Int, gender: String)
  case class ShortUser(name: String)

  val full = FullUser(name="francisco", age=23, gender="male")

  test("IMPLICIT CONVERSTION: Can convert an object into another when needed behind the scenes") {
    implicit def full2Short(user: FullUser): ShortUser = {
      ShortUser(user.name)
    }

    def displayName(short: ShortUser): String = short.name

    assert("francisco" == displayName(full))
  }

  test("RICH WRAPPER: Can add a new method to another class LIFTING OR WRAPPING") {
    case class Person(name: String) {
      def greet(): String = s"Hello person: $name"
    }

    case class Counter(number: Int) {
      def greet(): String = s"Hello number: $number"
    }

    implicit def liftStringToperson(str: String): Person = Person(str)
    implicit def liftIntToperson(number: Int): Counter = Counter(number)

    assert("Hello person: Maria" === "Maria".greet())
    assert("Hello number: 5" === 5.greet())
  }

  test("RICH WRAPPER: Can use a shourtcut to do the same") {
    implicit class StringToperson(str: String) {
      def greet(): String = s"Hello $str"
    }

    assert("Hello Maria" === "Maria".greet())
  }

  test("Same, more complete") {
    trait ShowName {
      def appendToName(sufix: String): String
    }

    implicit class full2Short(user: FullUser) extends ShowName {
      def appendToName(sufix: String): String = "FULL: " + user.name + sufix
    }

    implicit class shortToFull(user: ShortUser) extends ShowName {
      def appendToName(sufix: String): String = "SHORT: " + user.name + sufix
    }

    assert("FULL: francisco!!" == full.appendToName("!!"))
  }
}
