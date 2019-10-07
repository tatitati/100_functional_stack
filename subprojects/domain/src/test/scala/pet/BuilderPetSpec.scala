package domain.test.pet

import cats.data.State
import domain.order.OrderId
import domain.pet.Pet
import domain.test.Seed
import domain.test.pet.BuilderPetOps.BuilderState
import org.scalatest.FunSuite

class BuilderPetSpec extends FunSuite {
  test("I can use Monad State to use pet builder") {
      val (_, any) = BuilderPetOps.any().run(Seed(100)).value

      val createPet: State[BuilderState, Pet] = for{
        _ <- BuilderPetOps.withAge(34)
        _ <- BuilderPetOps.withPrice(110)
        _ <- BuilderPetOps.withOrderId(OrderId("update_order_id"))
        built <- BuilderPetOps.build()
      } yield built

      assert(
        (
          BuilderState(OrderId("update_order_id"),34,"any name",110),
          Pet(OrderId("update_order_id"),"any name",34,110)
        ) == createPet.run(any).value
      )
  }
}
