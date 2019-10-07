package learning.test.scala.FPDataStructures

import org.scalatest.FunSuite

class FPMonoidSpec extends FunSuite {

  // definition
  trait Monoid[A] {
    def id: A // an identity element
    def op(x: A, y: A): A // an associative operation
  }

  def stringMonoid = new Monoid[String] {
    def id = ""
    def op(x: String, y: String) = x + y
  }

  def listMonoid[A] = new Monoid[List[A]] {
    def id = Nil
    def op(x: List[A], y: List[A]) = x ++ y
  }

  def optionMonoid[A](anotherMonoid: Monoid[A]) = new Monoid[Option[A]] {
      def id = None

      def op(x: Option[A], y: Option[A]): Option[A] = (x,y) match {
        case (x, None) => x
        case (None, y) => y
        case (Some(x),Some(y)) => Some(anotherMonoid.op(x,y)) // here we use the A monoid to add two As
      }
    }

  assert("aabb" === stringMonoid.op("aa", "bb"))
  assert("" === stringMonoid.id)

  assert(List("aa", "bb", "cc", "dd") === listMonoid.op(List("aa", "bb"), List("cc", "dd")))
  assert(Nil === listMonoid.id)

  assert(None === optionMonoid(stringMonoid).id)
  assert(
    Some("whateversomething") == optionMonoid(stringMonoid).op(Some("whatever"), Some("something"))
  )
}
