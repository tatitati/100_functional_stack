package learning.test.cats.Monads

import cats.{Eval, Id, Monad}
import cats.instances.list._
import cats.instances.option._
import org.scalatest.FunSuite

// actually I NEED ONLY THESE TWO IMPORTS IN GENERAL WITH CATS, NO MORE
//import cats._
//import cats.implicits._

class EvalSpec
  extends FunSuite {

  //  trait Monad[F[_]] {
  //    def pure[A](value: A): F[A]
  //    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B
  //    def map[A, B](value: F[A])(func: A => B): F[B]
  //  }

  test("Eval monad") {
    val now = Eval.now(math.random.toInt + 1000)
    val later = Eval.later(math.random.toInt + 2000)
    val always = Eval.always(math.random.toInt + 2000)

    assert(
        now.isInstanceOf[Eval[Int]] &&
        later.isInstanceOf[Eval[Int]] &&
        always.isInstanceOf[Eval[Int]]
    )
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

    val result1 = sumSquare(Option(3), Option(4))
    val result2 = sumSquare(List(3), List(4))
    val result3 = sumSquare(3 : Id[Int], 4 : Id[Int])
    val result4 = sumSquare(Monad[Id].pure(3), Monad[Id].pure(4))
    val result5 = sumSquare(Eval.now(3), Eval.later(4))

    assert(Some(25) === result1)
    assert(List(25) === result2)
    assert(25 === result3)
    assert(25 === result4)
    assert(25 === result5.value)
  }
}
