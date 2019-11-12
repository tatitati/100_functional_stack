package infrastructure

sealed trait ErrorPet
final case object ErrorPetExist extends ErrorPet
final case object ErrorPetDontExist extends ErrorPet
