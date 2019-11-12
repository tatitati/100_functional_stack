package application

import cats.data._
import cats.effect._
import cats.implicits._
import domain.pet.Pet
import infrastructure.{PetDontExist, ErrorPetExist, RepositoryPet}

class ServicePet(petRepository: RepositoryPet) {

  def create(pet: Pet): IO[Either[ErrorPetExist.type, Unit]] = {
    val exists: IO[Boolean] = petRepository.exist(pet.name)
    val create: IO[Int] = petRepository.create(pet)

    exists.flatMap {
      case true => IO(ErrorPetExist.asLeft[Unit])
      case false => create.map(_ => ().asRight[ErrorPetExist.type])
    }
  }

  def find(pet:Pet): IO[Option[Pet]] = {
    petRepository.findByName(pet.name)
  }

//  def list: IO[List[Pet]] = {
//    petRepository.list()
//  }

//  def update(newage: Int, pet: Pet): IO[Either[PetDontExist.type, Unit]] = {
//    val exist: IO[Boolean] = petRepository.exist(pet.name)
//    val update: IO[Unit] = petRepository.updateAge(newage, pet)
//
//    val run: IO[Either[PetDontExist.type, Unit]] = exist.flatMap{
//      // I can do
//      // update.map(_ => ().asRight[PetDontExist.type])
//      // Right(Unit))
//      case true => update.map(_ => Right(Unit))
//      // I can do:
//      // PetDontExist.asLeft[Unit].pure[IO]
//      // IO{PetDontExist.asLeft[Unit]}
//      // IO(PetDontExist.asLeft[Unit])
//      // IO(Left(PetDontExist.asLeft[Unit])
//      // IO(Left(PetDontExist))
//      case false => IO(Left(PetDontExist))
//    }
//
//    run
//  }
}