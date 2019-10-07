package learning.test.scala.BehaviourInjection

import org.scalatest.FunSuite

class ImplicitConversionSpec extends FunSuite {

  test("IMPLICIT CONVERSTION: Can convert an object into another when needed behind the scenes") {
    case class FullUser(name: String, age: Int, gender: String)
    case class ShortUser(name: String)

    implicit def full2Short(user: FullUser): ShortUser = {
      ShortUser(user.name)
    }

    def exposeUser(user: ShortUser): ShortUser = user

    val full = FullUser(name="francisco", age=23, gender="male")
    // when calling to exposeUser I pass a full, however, this "expose()" is expecting a short user, and
    // we have an implicit function-conversion that can convert a FullUser into a ShortUser, so is converted
    // implicitly, we don't need to do the conversion on our own always
    assert(exposeUser(full).isInstanceOf[ShortUser])
  }

  test("RICH WRAPPER: Can add a new method to another class LIFTING OR WRAPPING") {
    // As we can treat injection-method as a conversion problem
    // We say that "We have an implicit conversion that converts the target type to a RICH WRAPPER"
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
}
