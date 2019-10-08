package domain.test.pet

import cats.data.State
import domain.order.OrderId
import domain.pet.Pet
import domain.test.{Faker, Seed}

object BuilderPet {

  case class BuilderState(surrogateId: Option[Long], age: Int, name: String, price: Int)

  def any(): State[Seed, BuilderState] = State { (seedLong:Seed) =>
    val randomInt: State[Seed, Int] = Faker.nextIntPositive()

    val createPet: State[Seed, BuilderState] = for {
      age <- randomInt
      price <- randomInt
    } yield BuilderState(None, age, "any name", price)

    createPet.run(seedLong).value
  }

  def withAge(withAge: Int): State[BuilderState, Unit] = State { (builderPet: BuilderState) =>
    val builderWithAge = builderPet.copy(age = withAge)
    State.set[BuilderState](builderWithAge).run(builderPet).value
  }

  def withPrice(withPrice: Int): State[BuilderState, Unit] = State { (builderPet: BuilderState) =>
    val builderWithAge = builderPet.copy(price = withPrice)
    State.set[BuilderState](builderWithAge).run(builderPet).value
  }

  def withName(withName: String, builderPet: BuilderState): BuilderState = {
    builderPet.copy(name = withName)
  }

  def withSurrogateId(withSurrogateId: Option[Long]): State[BuilderState, Unit] = State { builderPet:BuilderState =>
    val builderWithOrderId = builderPet.copy(surrogateId = withSurrogateId)
    State.set[BuilderState](builderWithOrderId).run(builderPet).value
  }

  def build(): State[BuilderState, Pet] = State{ builderState =>
    val built = Pet(
      surrogateId = builderState.surrogateId,
      age = builderState.age,
      name = builderState.name,
      price = builderState.price
    )
    State.pure[BuilderState, Pet](built).run(builderState).value
  }
}

