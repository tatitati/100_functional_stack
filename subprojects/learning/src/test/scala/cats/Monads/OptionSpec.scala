package learning.test.cats.Monads

import cats.Monad
import cats.instances.option._
import org.scalatest.FunSuite

class OptionSpec extends FunSuite {

  //  trait Monad[F[_]] {
  //    def pure[A](value: A): F[A]
  //    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B
  //    def map[A, B](value: F[A])(func: A => B): F[B]
  //  }

  test("pure()") {
    val result1 = Monad[Option].pure(3)
    assert(Some(3) === result1)

    import cats.syntax.applicative._ // to add (inject) the method pure() into our objects
    val result2 = 3.pure[Option]
    assert(Some(3) === result2)
  }

  test("map(), usually we dont want to use this function as is automatically used in a for{} loop") {
    val result1 = Monad[Option].map(Some(3))(a => 2*a)
    val result2 = Monad[Option].map(Some(3))(a => Some(a.toString))

    assert(Some(6) === result1)
    assert(Some(Some("3")) === result2)
  }

  test("flatMap(), usually we dont want to use this function as is automatically used in a for{} loop") {
    val result1 = Monad[Option].flatMap(Some(3))(a => Some(2*a))
    val result2 = Monad[Option].flatMap(Some(3))(a => Some(a.toString))

    assert(Some(6) === result1)
    assert(Some("3") === result2)
  }

  test("Syntax for flatmap and map function") {
    import cats.syntax.flatMap._ // to add (inject) the method flatmap() into our objects
    import cats.syntax.functor._ // to add (inject) the method map() into our objects

    // bound context here:
    // we are saying that for F[_] we have an implicit Monad[F[_]]
    def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] = {
      for {
        x <- a
        y <- b
      } yield x*x + y*y
    }

    val result1 = sumSquare(Option(3), Option(4))
    assert(Some(25) === result1)
  }

  test("use case2: Option is a monad itself, not needed for a Monad?") {
      val result = for {
        x <- Option(3)
        y <- Option(4)
      } yield x*x + y*y

    assert(Some(25) === result)
  }
}
