package infrastructure.test

import cats.effect._
import domain.order.OrderId
import domain.pet.Pet
import doobie.ConnectionIO
import infrastructure.RepositoryPet
import doobie.implicits._
import cats.implicits._
import doobie._
import infrastructue.CustomDbConnection
import org.scalatest.FunSuite

trait ResetCache extends FunSuite with CustomDbConnection {

  def reset(): IO[Int] = {
    val drop: ConnectionIO[Int] = sql"""
      DROP TABLE IF EXISTS pet
    """.update.run

    val create: ConnectionIO[Int] = sql"""
      CREATE TABLE pet (
        id      SERIAL primary key,
        name    VARCHAR NOT NULL,
        age     INTEGER NOT NULL,
        price   INTEGER NOT NULL
    )""".update.run

    val insert1: ConnectionIO[Int] = sql"insert into pet(name, age, price) values('Bolt', 17, 172)".update.run
    val insert2: ConnectionIO[Int] = sql"insert into pet(name, age, price) values('Lassie', 10, 230)".update.run

    // does it make sense this for{...}
    val aa: IO[Int] = for{
      _ <- drop.transact(xa)
      _ <- create.transact(xa)
      _ <- insert1.transact(xa)
      a <- insert2.transact(xa)
    } yield a

    aa
  }
}