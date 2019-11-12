package infrastructure

sealed trait PetExistError
final case object ErrorPetExist extends PetExistError
final case object PetDontExist extends PetExistError
