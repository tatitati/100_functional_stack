package learning.test.cats.MonadTransformers

import cats.data.{EitherT, OptionT}
import cats.instances.list._
import cats.syntax.applicative._
import org.scalatest.FunSuite

class OptionTSpec extends FunSuite {

  type ListOption[A] = OptionT[List, A]

  test("apply()") {
    case class User(name: String)

    val a = 32.pure[ListOption]
    val b = OptionT(List(Option(32)))

    assert(a == b)
  }

  test("Monad transformers are simply nesting flatMaps, so they access without needing nested loops to the inner value") {
    val a = OptionT(List(Option(32)))
    //    we have:
    //        OptionT
    //          List
    //            Option

    for{
      aVal <- a
    } yield {
      assert(32 === aVal , "Monad transformer cross two layers of monads in once (List and Option in here)")
    }

    val result = for{
      aVal <- a
    } yield aVal+2

    println(result) // OptionT(List(Some(34)))
  }

  test("same with Either") {
    import cats.instances.either._

    //  we want:
    //        OptionT
    //          Either
    //            Option

    type Maybe[A] = Either[String, A]
    type MaybeOption[A] = OptionT[Maybe, A]

    val a = 10.pure[MaybeOption] // OptionT(Right(Some(10))) === a
    val b = 5.pure[MaybeOption] // OptionT(Right(Some(5)) === b

    val result = for{
      aVal <- a
      bVal <- b
    } yield {
      assert(10 === aVal , "Monad transformer cross two layers of monads in once (Either and Option in here)")
      aVal + bVal
    }

    println(result) // OptionT(Right(Some(15)))
    assert(Right(Some(15)) == result.value, "I can unpack the value of a monad transformer using .value()")

    val result2 = result.value.map(_.getOrElse(-1))
    assert(Right(15) === result2)
  }

  test("I can Lift with pure(), but also I can lift using apply()") {
    type ErrorOr[A] = Either[String, A]
    val a = OptionT[ErrorOr, Int](Right(Some(10)))
    val b = OptionT[ErrorOr, Int](Left("fuck"))

    println(a) // OptionT(Right(Some(10)))
    println(b) // OptionT(Left(fuck))
  }
}
