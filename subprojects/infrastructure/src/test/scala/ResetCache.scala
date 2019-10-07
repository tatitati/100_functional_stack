package infrastructure.test

import cats.effect._
import domain.order.OrderId
import domain.pet.Pet
import infrastructure.PetRepository

trait ResetCache {
  self: PetRepository =>

  def reset(): IO[Unit] = {
    IO{
      cache = Map(
        "Bolt" -> Pet(OrderId("00001A"), "Bolt", 17, 172),
        "Lassie" -> Pet(OrderId("00002A"), "Lassie", 10, 230)
      )
    }
  }
}