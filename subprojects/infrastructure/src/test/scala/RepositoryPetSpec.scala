package infrastructure.test

import cats.data.OptionT
import cats.effect.IO
import domain.pet.Pet
import infrastructure.{MapperPet, PersistentPet, RepositoryPet}
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import infrastructue.CustomDbConnection
import doobie.implicits._

class RepositoryPetSpec extends FunSuite with BeforeAndAfterEach with ResetCache {

  val repo = new RepositoryPet()

  override def beforeEach() {
    val resetDb:IO[Int] = reset()
    resetDb.unsafeRunSync()
  }

  test("repo.create()"){
    val create: IO[Int] = repo.create(Pet(None, "colmillo_blanco", 8, 2))

    assert(1 == create.unsafeRunSync())
  }

  test("repo.findByName()") {
    val find1:IO[Option[Pet]] = repo.findByName("Bolt")
    val find2:IO[Option[Pet]] = repo.findByName("No existing")

    assert(Some(Pet(Some(1), "Bolt", 17, 172)) == find1.unsafeRunSync())
    assert(None == find2.unsafeRunSync())
  }

  test("repo.exist()") {
    val exist1:IO[Boolean] = repo.exist("Bolt")
    val exist2:IO[Boolean] = repo.exist("wrrroongg")

    assert(true == exist1.unsafeRunSync())
    assert(false == exist2.unsafeRunSync())
  }

//  test("repo.list()") {
//    val result:IO[List[Pet]] = repo.list()
//
//    assert(
//      List(Pet(OrderId("00001A"), "Bolt", 17, 172), Pet(OrderId("00002A"), "Lassie", 10, 230)) == result.unsafeRunSync()
//    )
//  }
//


//
//  test("repo.update()") {
//    val result:IO[Unit] = repo.updateAge(55, Pet(OrderId("00002"), "Bolt", 17, 33))
//
//    assert(() == result.unsafeRunSync)
//  }
}
