package domain.test.pet

import cats.data.State
import domain.order.OrderId
import domain.pet.Pet
import domain.test.Seed
import domain.test.pet.BuilderPet.BuilderState
import org.scalatest.FunSuite

class BuilderPetSpec extends FunSuite {
  test("I can use Monad State to use pet builder") {
      val (seed, anyBuilderState) = BuilderPet.any().run(Seed(100)).value

      val createPet: State[BuilderState, Pet] = for{
        _ <- BuilderPet.withAge(34)
        _ <- BuilderPet.withPrice(110)
        _ <- BuilderPet.withSurrogateId(None)
        built <- BuilderPet.build()
      } yield built

      assert(
        (
          BuilderState(None,34,"any name",110),
          Pet(None,"any name",34,110)
        )
          == createPet.run(anyBuilderState).value
      )
  }
}
