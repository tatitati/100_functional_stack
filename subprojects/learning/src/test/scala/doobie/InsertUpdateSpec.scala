package learning.test.doobie

import cats.effect.IO
import org.scalatest.FunSuite
import doobie.implicits._
import cats.implicits._
import doobie._

class InsertUpdateSpec extends FunSuite with CustomDbConnection {

  test("Doobie: CREATE TABLE / DROP TABLE"){
    val drop: ConnectionIO[Int] = sql"""
      DROP TABLE IF EXISTS person
    """.update.run

    val create: ConnectionIO[Int] = sql"""
      CREATE TABLE person (
        id   SERIAL,
        name VARCHAR NOT NULL UNIQUE,
        age  SMALLINT
    )""".update.run

    val result: Int = (drop, create)
      .mapN(_ + _) // FRE
      .transact(xa) // IO
      .unsafeRunSync // INTEGER

  }

  test("Doobie: INSERT"){
    case class Person(id: Long, name: String, age: Option[Short])

    def insert(name: String, age: Option[Short]): Update0 =
      sql"insert into person (name, age) values ($name, $age)".update

    val idNewPerson: Int = insert("Alice", Some(12))
      .run
      .transact(xa)
      .unsafeRunSync

    val listPerson = sql"select id, name, age from person"
      .query[Person]
      .to[List]
      .transact(xa)
      .unsafeRunSync

    assert(1 == idNewPerson)
    assert(List(Person(1,"Alice",Some(12))) == listPerson)
  }

  test("Doobie: UPDATE"){
    case class Person(id: Long, name: String, age: Option[Short])
    val resultUpdate = sql"update person set age = 15 where name = 'Alice'".update.run.transact(xa).unsafeRunSync
    val resultSelect = sql"select id, name, age from person".query[Person].to[List].transact(xa).unsafeRunSync

    assert(1 == resultUpdate)
    assert(List(Person(1,"Alice",Some(15))) == resultSelect)
  }

  test("Doobie: RETRIEVING ID on INSERT"){
    case class Person(id: Long, name: String, age: Option[Short])

    def insert(name: String, age: Option[Short]): ConnectionIO[Person] =
      for {
        _  <- sql"insert into person (name, age) values ($name, $age)".update.run
        id <- sql"select lastval()".query[Long].unique
        p  <- sql"select id, name, age from person where id = $id".query[Person].unique
      } yield p

    val ioResult: IO[Person] = insert("jaime", Some(43)).transact(xa)
    val personResult: Person = ioResult.unsafeRunSync

    assert(Person(2,"jaime",Some(43)) == personResult)
  }
}
