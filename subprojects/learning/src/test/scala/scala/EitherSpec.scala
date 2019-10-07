package learning.test.scala

import org.scalatest.FunSuite

import scala.util.Either.LeftProjection

class EitherSpec extends FunSuite {

  test("Can create Either") {
    val a = Right(5)

    assert(5 === a.right.get)
    assert(LeftProjection(Right(5)) === a.left)
  }
}
