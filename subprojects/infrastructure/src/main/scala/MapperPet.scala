package infrastructure

import domain.pet.Pet

object MapperPet {
  def toDomain(persistent: PetPersistent): Pet = {
    Pet(
      persistent.orderId,
      persistent.name,
      persistent.age,
      persistent.price
    )
  }

  def toPersistent(pet: Pet): PetPersistent = {
    PetPersistent(
      pet.surrogateId,
      pet.name,
      pet.age,
      pet.price
    )
  }
}
