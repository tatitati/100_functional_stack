package infrastructure

import cats.effect.IO
import domain.order.OrderId
import domain.pet.Pet

class PetRepository {
  var cache: Map[String, Pet] = Map(
    "Bolt" -> Pet(OrderId("00001A"), "Bolt", 17, 172),
    "Lassie" -> Pet(OrderId("00002A"), "Lassie", 10, 230)
  )

  def create(pet: Pet): IO[Unit] = {
    IO{
      cache += (pet.name -> pet)
    }
  }

  def findByName(name: String): IO[Option[Pet]] = {
    IO {
      cache.contains(name) match {
        case true => Some(cache(name))
        case false => None
      }
    }
  }

  def list(): IO[List[Pet]] = {
    IO{
      cache.map(_._2).toList
    }
  }

  def exist(name: String): IO[Boolean] = {
    IO{
      cache.contains(name)
    }
  }

  def updateAge(newage: Int, pet: Pet): IO[Unit] = {
    IO{
      cache -= pet.name
      cache += (pet.name -> Pet(pet.orderId, pet.name, newage, pet.price))
    }
  }

  def updatePrice(price: Int, pet: Pet): IO[Unit] = {
    IO{
      cache -= pet.name
      cache += (pet.name -> Pet(pet.orderId, pet.name, pet.age, pet.price))
    }
  }
}
