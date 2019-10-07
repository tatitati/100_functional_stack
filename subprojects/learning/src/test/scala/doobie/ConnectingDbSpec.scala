package learning.test.doobie

import org.scalatest.FunSuite
import doobie._
import cats._
import cats.effect._
import cats.implicits._
import doobie.implicits._
import doobie.util.ExecutionContexts

class ConnectingDbSpec extends FunSuite with CustomDbConnection {

  test("ConnectionIO is converted into IO"){
    val program1 = 42.pure[ConnectionIO]
    val io: IO[Int] = program1.transact(xa)

    assert(42 == io.unsafeRunSync)
  }

  test("Can request to db") {
    val program2: ConnectionIO[Int] = sql"select 42".query[Int].unique
    val io2: IO[Int] = program2.transact(xa)

    assert(42 == io2.unsafeRunSync)
  }

  test("Can request multiple things to db"){
    val program3: ConnectionIO[(Int, String)] = for {
        a <- sql"select 42".query[Int].unique
        b <- sql"select 'something'".query[String].unique
      } yield (a, b)

    val io3: IO[(Int, String)] = program3.transact(xa)
    val result: (Int, String) = io3.unsafeRunSync

    assert(result == (42, "something"))
  }
}
