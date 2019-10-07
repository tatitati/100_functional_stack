package application.test

import application.PetService
import cats.effect.IO
import domain.order.OrderId
import domain.pet.Pet
import infrastructure.{PetDontExist, PetExist, PetRepository}
import infrastructure.test.ResetCache
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class PetServiceSpec extends FunSuite with BeforeAndAfterEach{

  var repository = new PetRepository() with ResetCache

  override def afterEach() {
    val reset:IO[Unit] = repository.reset()
    reset.unsafeRunSync()
    repository = repository
  }

  test("service.create()") {
    val service = new PetService(repository)

    val programRight: IO[Either[PetExist.type, Unit]] = service.create(Pet(OrderId("00001A"), "toby" ,32, 32))
    val resultRight = programRight.unsafeRunSync()
    assert(Right(()) == resultRight, "Should be right")

    val programLeft: IO[Either[PetExist.type, Unit]] = service.create(Pet(OrderId("00001A"), "Bolt" ,17, 433))
    val resultLeft = programLeft.unsafeRunSync()
    assert(Left(PetExist) == resultLeft, "Should be left")
  }

  test("service.find()") {
    val service = new PetService(repository)
    println(repository.cache)
    val result1:IO[Option[Pet]] = service.find(Pet(OrderId("00001A"), "nonexisting", 23, 100))
    assert(None === result1.unsafeRunSync(), "Should be None")

    val result2:IO[Option[Pet]] = service.find(Pet(OrderId("00001A"), "Bolt", 23, 100))
    assert(Some(Pet(OrderId("00001A"), "Bolt",17, 172)) === result2.unsafeRunSync(), "Should be Some(..)")
  }

  test("service.update()") {
    val service = new PetService(repository)

    val update1:IO[Either[PetDontExist.type, Unit]] = service.update(666, Pet(OrderId("00001A"), "Bolt", 32, 300))
    assert(Right(()) == update1.unsafeRunSync())

    val update2:IO[Either[PetDontExist.type, Unit]] = service.update(666, Pet(OrderId("00001A"), "non_existing", 32, 4343))
    assert(Left(PetDontExist) == update2.unsafeRunSync())
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