package infrastructure

import cats.effect.IO
import domain.pet.Pet
import doobie.ConnectionIO
import doobie.implicits._
import infrastructue.CustomDbConnection

class PetRepository extends CustomDbConnection{

  def create(pet: Pet): IO[Int] = {
    sql"""insert into pet(name, age, price) values(${pet.name}, ${pet.age}, ${pet.price})"""
      .update
      .run
      .transact(xa)
  }

  def findByName(name: String): IO[Option[Pet]] = ???
//  {
//    IO {
//      cache.contains(name) match {
//        case true => Some(cache(name))
//        case false => None
//      }
//    }
//  }

  def list(): IO[List[Pet]] = ???
//  {
//    IO{
//      cache.map(_._2).toList
//    }
//  }

  def exist(name: String): IO[Boolean] = ???
//  {
//    IO{
//      cache.contains(name)
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
