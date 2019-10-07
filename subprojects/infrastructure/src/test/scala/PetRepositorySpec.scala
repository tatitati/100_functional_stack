package infrastructure.test

import scala.util.Random
import cats.effect.IO
import domain.order.OrderId
import domain.pet.Pet
import infrastructure.PetRepository
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class PetRepositorySpec extends FunSuite with BeforeAndAfterEach{

  val repo = new PetRepository() with ResetCache

  override def afterEach() {
    val reset:IO[Unit] = repo.reset()
    reset.unsafeRunSync()
  }

  test("repo.create()"){
    val create: IO[Unit] = repo.create(Pet(OrderId("00001"), "colmillo_blanco", 8, 23))

    assert(() == create.unsafeRunSync())
  }

  test("repo.findByName()") {
    // repo.findByName() ::
    val find1:IO[Option[Pet]] = repo.findByName("Bolt")
    val find2:IO[Option[Pet]] = repo.findByName("No existing")

    assert(Some(Pet(OrderId("00001A"), "Bolt", 17, 172)) == find1.unsafeRunSync())
    assert(None == find2.unsafeRunSync())
  }

  test("repo.list()") {
    val result:IO[List[Pet]] = repo.list()

    assert(
      List(Pet(OrderId("00001A"), "Bolt", 17, 172), Pet(OrderId("00002A"), "Lassie", 10, 230)) == result.unsafeRunSync()
    )
  }

  test("repo.exist()") {
    val exist:IO[Boolean] = repo.exist("Bolt")

    assert(true == exist.unsafeRunSync())
  }

  test("repo.update()") {
    val result:IO[Unit] = repo.updateAge(55, Pet(OrderId("00002"), "Bolt", 17, 33))

    assert(() == result.unsafeRunSync)
  }
}
