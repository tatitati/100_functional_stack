package learning.test.cats.Monads

import cats.{Eval, Id, Monad}
import cats.instances.list._
import org.scalatest.FunSuite

// actually I NEED ONLY THESE TWO IMPORTS IN GENERAL WITH CATS, NO MORE
//import cats._
//import cats.implicits._

class ListSpec extends FunSuite {

  //  trait Monad[F[_]] {
  //    def pure[A](value: A): F[A]
  //    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B
  //    def map[A, B](value: F[A])(func: A => B): F[B]
  //  }

  test("pure()") {
    import cats.implicits._

    val result1 = Monad[List].pure(3)
    val result2 = 6.pure[List]

    assert(List(3) == result1)
    assert(List(6) == result2)
  }

  test("flatMap()") {
    val result1 = Monad[List].flatMap(List(3))(a => List(a, a*10))
    assert(List(3, 30) === result1)
  }

  test("map()") {
    val result1 = Monad[List].map(List(3))(a => a + 10)
    assert(List(13) === result1)
  }

  test("use case") {
    import cats.syntax.flatMap._
    import cats.syntax.functor._


    // bound context here:
    // we are saying that for F[_] we have an implicit Monad[F[_]]
    def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] = {
      for {
        x <- a
        y <- b
      } yield x*x + y*y
    }

    val result2 = sumSquare(List(3), List(4))
    assert(List(25) === result2)
  }
}
