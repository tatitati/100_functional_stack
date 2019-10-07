package learning.test.cats.MonadTransformers

import cats.data.{EitherT, OptionT}
import cats.effect.IO
import cats.instances.list._
import cats.syntax.all._
import org.scalatest.FunSuite
import cats.instances.either._
import scala.concurrent.Future

class EithertSpec extends FunSuite{

  test("EitherT.apply()") {
    val listRight: List[Either[String, Int]] = List(Right(250))
    val numberFET: EitherT[List, String, Int] = EitherT(listRight)

    assert(List(Right(250)) == numberFET.value)
  }

  test("EitherT.asLeft() and EitherT.asRight()") {

  }

  test("EitherT.right() and EitherT.left()") {
    import cats.implicits._

    val someInt: Option[Int] = Some(5)
    val someText: Option[String] = Some("Not a number")

    val someRightInt: EitherT[Option, String, Int] = EitherT.right(someInt)
    val SomeLeftText: EitherT[Option, String, Int] = EitherT.left(someText)

    assert(Some(Right(5)) == someRightInt.value)
    assert(Some(Left("Not a number")) == SomeLeftText.value)
  }

  test("EitherT.FromEither()") {
    val listRight1: EitherT[List, String, Int] = EitherT.fromEither(Right(100))
    assert(List(Right(100)) == listRight1.value)

    val listLeft: EitherT[List, String, Int] = EitherT.fromEither(Left("Not a number"))
    assert(List(Left("Not a number")) == listLeft.value)
  }

  test("EitherT.fromOption()") {
    val listLeft = EitherT.fromOption[List](None, "option not defined")
    assert(List(Left("option not defined")) == listLeft.value)

    val listRight = EitherT.fromOption[List](Some(2), "option not defined")
    assert(List(Right(2)) == listRight.value)
  }

  test("EitherT.fromOptionF()") {
    val myOptionList: List[Option[Int]] = List(
      None,
      Some(2),
      Some(3),
      None,
      Some(5)
    )
    val result = EitherT.fromOptionF(myOptionList, "text if None")

    assert(EitherT(
        List(
          Left("text if None"),
          Right(2),
          Right(3),
          Left("text if None"),
          Right(5)
        )
      ) == result
    )
  }

//  test("EitherT.liftF()"){
//    val value = Option(10).pure[IO] // => IO(Some(10))
//
//    val result = EitherT.liftF(value)
////    assert(true === result.value)
//
//
//  }
}
