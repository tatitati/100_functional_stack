package infrastructure.test

import domain.test.pet.BuilderPet
import infrastructure.MapperPet
import org.scalatest.FunSuite

class MapperPetSpec extends FunSuite {

  test("MapperPet.toDomain()") {
    val persistent = for{
      domain <- BuilderPet.any()
      persistent <- MapperPet.toPersistent(domain)
    } yield persistent
  }

  test("MapperPet.toPersistence()") {
    val persistent = for{
      domain <- BuilderPet.any()
      persistent <- MapperPet.toPersistent(domain)
    } yield persistent
  }
}
