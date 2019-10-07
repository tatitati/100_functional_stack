package learning.test.cats.Monads

import cats.syntax.either._
import org.scalatest.FunSuite

class EitherSpec extends FunSuite {

  test(".asRight(), .asLeft()") {
    val right = 4.asRight[Boolean]
    assert(Right(4) == right)

    val left = "asdfasdf".asLeft[Boolean]
    assert(Left("asdfasdf") == left)
  }

  test("Either.left(), Either.right()") {
    assert(Left("hello") == Either.left("hello"))
    assert(Right(5) == Either.right(5))
  }

  test("Either.fromOption()") {
    val leftFromNone = Either.fromOption[String, Int](None, "Text in Left")
    assert(Left("Text in Left") == leftFromNone)

    val rightFromSome = Either.fromOption[String, Int](Some(1), "")
    assert(Right(1) == rightFromSome)
  }

  test("map() and flatMap() operates with the nested item") {
    val e1: Either[String, Int] = Right(5)
    val e2 = e1.right.map(_ + 1)

    assert(Right(6) == e2)
  }
}
