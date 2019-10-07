package learning.test.cats.Monads

import cats.{Eval, Id, Monad}
import cats.instances.list._
import cats.instances.option._
import org.scalatest.FunSuite


// actually I NEED ONLY THESE TWO IMPORTS IN GENERAL WITH CATS, NO MORE
//import cats._
//import cats.implicits._

class IdSpec
  extends FunSuite {

  //  trait Monad[F[_]] {
  //    def pure[A](value: A): F[A]
  //    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B
  //    def map[A, B](value: F[A])(func: A => B): F[B]
  //  }

  test("ID") {
    val result0 = Monad[Id].pure("Dave")
    val result1 = "Dave" : Id[String]
    val result2 = 34 : Id[Int]
    val result3 = List(1,2,3) : Id[List[Int]]

    assert(result0.isInstanceOf[String]     && "Dave" === result0)
    assert(result1.isInstanceOf[String]     && "Dave" === result1)
    assert(result2.isInstanceOf[Int]        && 34 === result2)
    assert(result3.isInstanceOf[List[Int]]  && List(1,2,3) === result3)
  }

  test("ID operations") {
    val result1 = Monad[Id].pure(3)
    val result2 = Monad[Id].flatMap(3)(_ + 2)

    assert(3 === result1)
    assert(5 === result2)
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

    assert(Some(25) === result1)
    assert(List(25) === result2)
    assert(25 === result3)
    assert(25 === result4)
  }

  test("pure") {
    import cats.implicits._

    val result4 = 3.pure[Option]
    val result5 = Option(3).pure[Option]
    val result6 = Option(3).pure[List]

    assert(Some(3) === result4)
    assert(Some(Some(3)) === result5)
    assert(List(Some(3)) === result6)
  }
}
