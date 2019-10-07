package infrastructure.test

import scala.util.Random
import cats.effect.IO
import domain.order.OrderId
import domain.pet.Pet
import infrastructure.PetRepository
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class PetRepositorySpec extends FunSuite with BeforeAndAfterEach with ResetCache {

  val repo = new PetRepository()

  override def beforeEach() {
    val resetDb:IO[Int] = reset()
    resetDb.unsafeRunSync()
  }

  test("repo.create()"){
    val create: IO[Int] = repo.create(Pet(OrderId("doesntmatter"), "colmillo_blanco", 8, 2))

    assert(1 == create.unsafeRunSync())
  }
//
//  test("repo.findByName()") {
//    // repo.findByName() ::
//    val find1:IO[Option[Pet]] = repo.findByName("Bolt")
//    val find2:IO[Option[Pet]] = repo.findByName("No existing")
//
//    assert(Some(Pet(OrderId("00001A"), "Bolt", 17, 172)) == find1.unsafeRunSync())
//    assert(None == find2.unsafeRunSync())
//  }
//
//  test("repo.list()") {
//    val result:IO[List[Pet]] = repo.list()
//
//    assert(
//      List(Pet(OrderId("00001A"), "Bolt", 17, 172), Pet(OrderId("00002A"), "Lassie", 10, 230)) == result.unsafeRunSync()
//    )
//  }
//
//  test("repo.exist()") {
//    val exist:IO[Boolean] = repo.exist("Bolt")
//
//    assert(true == exist.unsafeRunSync())
//  }
//
//  test("repo.update()") {
//    val result:IO[Unit] = repo.updateAge(55, Pet(OrderId("00002"), "Bolt", 17, 33))
//
//    assert(() == result.unsafeRunSync)
//  }
}
