package infrastructure.test

import cats.data.OptionT
import cats.effect.IO
import domain.pet.Pet
import infrastructure.{MapperPet, PersistentPet, RepositoryPet}
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import infrastructue.CustomDbConnection
import doobie.implicits._
import org.http4s.CharsetRange.*

class RepositoryPetSpec extends FunSuite with BeforeAndAfterEach with ResetCache {

  val repo = new RepositoryPet()

  override def beforeEach() {
    val resetDb:IO[Int] = reset()
    resetDb.unsafeRunSync()
  }

  test(".create()"){
    val create: IO[Int] = repo.create(Pet(None, "colmillo_blanco", 8, 2))

    assert(1 == create.unsafeRunSync())
  }

  test(".findByName()") {
    val findSome:IO[Option[Pet]] = repo.findByName("Bolt")
    val findNone:IO[Option[Pet]] = repo.findByName("No existing")

    assert(Some(Pet(Some(1), "Bolt", 17, 172)) == findSome.unsafeRunSync())
    assert(None == findNone.unsafeRunSync())
  }

  test(".exist()") {
    val exist1:IO[Boolean] = repo.exist("Bolt")
    val exist2:IO[Boolean] = repo.exist("wrrroongg")

    assert(true == exist1.unsafeRunSync())
    assert(false == exist2.unsafeRunSync())
  }

  test(".count()") {
    val result:IO[Int] = repo.count()
    assert(2 == result.unsafeRunSync())
  }

  test(".list()"){
    val pets: IO[List[Pet]] = repo.list()

    assert(
      List(Pet(Some(1),"Bolt",17,172), Pet(Some(2),"Lassie",10,230))
      == pets.unsafeRunSync())
  }

  test(".listStream()"){
    val pets = repo.listStream()


  }

//
//  test("repo.update()") {
//    val result:IO[Unit] = repo.updateAge(55, Pet(OrderId("00002"), "Bolt", 17, 33))
//
//    assert(() == result.unsafeRunSync)
//  }
}
