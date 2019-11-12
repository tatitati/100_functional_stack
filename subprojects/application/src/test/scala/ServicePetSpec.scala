package application.test

import application.ServicePet
import cats.effect.IO
import domain.order.OrderId
import domain.pet.Pet
import infrastructure.{PetDontExist, ErrorPetExist, RepositoryPet}
import infrastructure.test.ResetCache
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class ServicePetSpec extends FunSuite with BeforeAndAfterEach with ResetCache {

  var repository = new RepositoryPet()
  val service = new ServicePet(repository)

  override def afterEach() {
    val resetDb:IO[Int] = reset()
    resetDb.unsafeRunSync()
  }

  test("service.create()") {
    val programRight: IO[Either[ErrorPetExist.type, Unit]] = service.create(Pet(None, "toby" ,32, 32))
    val programLeft: IO[Either[ErrorPetExist.type, Unit]] = service.create(Pet(None, "Bolt" ,17, 433))

    assert(Right(()) == programRight.unsafeRunSync(), "Should be right")
    assert(Left(ErrorPetExist) == programLeft.unsafeRunSync(), "Should be left")
  }

  test("service.find()") {
    val resultNone:IO[Option[Pet]] = service.find(Pet(Some(32), "nonexisting", 23, 100))
    val resultSome:IO[Option[Pet]] = service.find(Pet(Some(43), "Bolt", 23, 100))

    assert(None === resultNone.unsafeRunSync(), "Should be None")
    assert(Some(Pet(Some(1), "Bolt",17, 172)) === resultSome.unsafeRunSync(), "Should be Some(..)")
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