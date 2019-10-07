package learning.test.cats.Monads

import cats.Monad
import cats.data.OptionT
import cats.effect.IO
import cats.implicits._
import org.scalatest.FunSuite

class IOSpec extends FunSuite {
  test(".pure[IO]") {
    case class User(name: String)

    val user = User("manolo")
    val IoUser1 = user.pure[IO] // lift User to IO

    assert(IoUser1.isInstanceOf[IO[User]])
  }

  test("use case") {
      val program: IO[Unit] = for {
        _ <- IO { println("Introduce your name") }
      } yield()

      program.unsafeRunSync()
  }
}
