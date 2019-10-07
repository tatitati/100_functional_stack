package learning.test.scala.BehaviourInjection

import org.scalatest.FunSuite

class TypeClassesSpec extends FunSuite {
    sealed trait Animal
    final case class Dog(name: String) extends Animal

    // type class
    trait BehavesLikeHuman[A] {
      def speak(a: A): String
    }

    // type class INSTANCES:
    // (usually people put these implicits inside another object "<something>Instances")
    implicit val dogBehavesLikeHuman = new BehavesLikeHuman[Dog] {
      def speak(dog: Dog): String = {
        s"I'm a dog named: ${dog.name}"
      }
    }

    // type class SYNTAX:
    // (this is the interfaces that the consumer will use)
    // usually people put these implicits inside another object "<something>Syntax"
    implicit class BehavesLikeHumanOps[A](value: A) {
      def saySomething(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): String = {
        behavesLikeHumanInstance.speak(value)
      }
    }

    test("Type classes can add new behaviour to existing classes") {
      val rover = Dog("Rover")
      assert("I'm a dog named: Rover" === rover.saySomething)
    }
}
