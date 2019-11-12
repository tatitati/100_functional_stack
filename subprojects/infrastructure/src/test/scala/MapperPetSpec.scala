package infrastructure.test

import cats.data.State
import domain.pet.Pet
import domain.test.Seed
import domain.test.pet.BuilderPet
import domain.test.pet.BuilderPet.BuilderState
import infrastructure.{MapperPet, PersistentPet}
import org.scalatest.FunSuite

class MapperPetSpec extends FunSuite {

  test(".toDomain()") {
    val (seed, anyBuilderState) = BuilderPet.any().run(Seed(100)).value

    val createPet: State[BuilderState, Pet] = for{
      _ <- BuilderPet.withAge(34)
      _ <- BuilderPet.withPrice(110)
      _ <- BuilderPet.withSurrogateId(None)
      built <- BuilderPet.build()
    } yield built

    val builtPet = createPet.runA(anyBuilderState).value
    val persistent = MapperPet.toPersistent(builtPet)

    assert(PersistentPet(None,"any name",34,110) == persistent)
  }

//  test("MapperPet.toPersistence()") {
//    val persistent = for{
//      domain <- BuilderPet.any()
//      persistent <- MapperPet.toPersistent(domain)
//    } yield persistent
//  }
}
