package infrastructure

import domain.pet.Pet

object MapperPet {
  def toDomain(persistent: PersistentPet): Pet = {
    Pet(
      persistent.orderId,
      persistent.name,
      persistent.age,
      persistent.price
    )
  }

  def toPersistent(pet: Pet): PersistentPet = {
    PersistentPet(
      pet.surrogateId,
      pet.name,
      pet.age,
      pet.price
    )
  }
}
