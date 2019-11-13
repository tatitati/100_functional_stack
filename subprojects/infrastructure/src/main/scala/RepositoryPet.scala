package infrastructure

import cats.effect.IO
import domain.pet.Pet
import infrastructue.CustomDbConnection
import cats.data.Nested
import doobie.implicits._
import cats.implicits._

class RepositoryPet extends CustomDbConnection{

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
      val result: IO[Option[PersistentPet]] = sql"""
          select *
          from pet
          where name = ${name}
      """
      .query[PersistentPet]
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
      .query[PersistentPet]
      .option
      .transact(xa)
      .map{
        case None => false
        case _ => true
    }
  }

  def count(): IO[Int] = {
    sql"""
          select count(*)
          from pet
        """
      .query[Int]
      .unique
      .transact(xa)
  }

  def list(): IO[List[Pet]] = {
    val result: IO[List[PersistentPet]] = sql"""
          select *
          from pet
        """
      .query[PersistentPet]
      .to[List]
      .transact(xa)

      result.map(_.map(MapperPet.toDomain(_)))

     // @TODO
     //val nested1: Nested[IO, List, PersistentPet] = Nested(result)
     //nested1.map(MapperPet.toDomain(_)).value
  }

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
