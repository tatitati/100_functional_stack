package infrastructure

sealed trait PetExistError
final case object PetExist extends PetExistError
final case object PetDontExist extends PetExistError
