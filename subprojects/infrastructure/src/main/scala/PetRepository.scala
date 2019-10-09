package infrastructure

import cats.data.OptionT
import cats.effect.IO
import domain.pet.Pet
import infrastructue.CustomDbConnection
import doobie.implicits._
import cats.implicits._

class PetRepository extends CustomDbConnection{

  def create(pet: Pet): IO[Int] = {
    val petPersistent = MapperPet.toPersistent(pet)

    sql"""
          insert into
          pet(name, age, price)
          values(${petPersistent.name}, ${petPersistent.age}, ${petPersistent.price})
      """
      .update
      .run
      .transact(xa)
  }

  def findByName(name: String): IO[Option[Pet]] = {
      val result: IO[Option[PetPersistent]] = sql"""
          select *
          from pet
          where name = ${name}
      """
      .query[PetPersistent]
      .option
      .transact(xa)

      result.map{
        case Some(value) => Some(MapperPet.toDomain(value))
        case None => None
      }
  }

  def exist(name: String): IO[Boolean] = {
    sql"""
          select *
          from pet
          where name = ${name}
        """
      .query[Pet]
      .option
      .transact(xa)
      .map{
        case None => false
        case _ => true
    }
  }

  def list(): IO[List[Pet]] = ???
  //  {
  //    IO{
  //      cache.map(_._2).toList
  //    }
  //  }

  def updateAge(newage: Int, pet: Pet): IO[Unit] = ???
//  {
//    IO{
//      cache -= pet.name
//      cache += (pet.name -> Pet(pet.orderId, pet.name, newage, pet.price))
//    }
//  }

  def updatePrice(price: Int, pet: Pet): IO[Unit] = ???
//  {
//    IO{
//      cache -= pet.name
//      cache += (pet.name -> Pet(pet.orderId, pet.name, pet.age, pet.price))
//    }
//  }
}
