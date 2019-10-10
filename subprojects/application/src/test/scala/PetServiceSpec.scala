package application.test

import application.PetService
import cats.effect.IO
import domain.order.OrderId
import domain.pet.Pet
import infrastructure.{PetDontExist, PetExist, PetRepository}
import infrastructure.test.ResetCache
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class PetServiceSpec extends FunSuite with BeforeAndAfterEach with ResetCache {

  var repository = new PetRepository()
  val service = new PetService(repository)

  override def afterEach() {
    val resetDb:IO[Int] = reset()
    resetDb.unsafeRunSync()
    repository = repository
  }

  test("service.create()") {
    val programRight: IO[Either[PetExist.type, Unit]] = service.create(Pet(None, "toby" ,32, 32))
    val programLeft: IO[Either[PetExist.type, Unit]] = service.create(Pet(None, "Bolt" ,17, 433))

    val resultRight = programRight.unsafeRunSync()
    val resultLeft = programLeft.unsafeRunSync()

    assert(Right(()) == resultRight, "Should be right")
    assert(Left(PetExist) == resultLeft, "Should be left")
  }

  test("service.find()") {
    val result1:IO[Option[Pet]] = service.find(Pet(Some(32), "nonexisting", 23, 100))
    val result2:IO[Option[Pet]] = service.find(Pet(Some(43), "Bolt", 23, 100))

    assert(None === result1.unsafeRunSync(), "Should be None")
    assert(Some(Pet(Some(1), "Bolt",17, 172)) === result2.unsafeRunSync(), "Should be Some(..)")
  }

  test("service.update()") {
//    val service = new PetService(repository)
//
//    val update1:IO[Either[PetDontExist.type, Unit]] = service.update(666, Pet(OrderId("00001A"), "Bolt", 32, 300))
//    assert(Right(()) == update1.unsafeRunSync())
//
//    val update2:IO[Either[PetDontExist.type, Unit]] = service.update(666, Pet(OrderId("00001A"), "non_existing", 32, 4343))
//    assert(Left(PetDontExist) == update2.unsafeRunSync())
  }
//
//  test("service CANNOT create a user") {
//    val service = new PetService(new PetRepository())
//    val petToby = Pet(name = "toby")
//
//    val result1 = service.create(petToby)
//    val result2 = service.create(petToby)
//
//    val check = for {
//      petCreated <- result2
//    } yield {
//      println("This output is NOT visible")
//      petCreated
//    }
//
//    assert(check.isLeft)
//  }
}