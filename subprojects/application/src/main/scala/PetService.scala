package application

import cats.data._
import cats.effect._
import cats.implicits._
import domain.pet.Pet
import infrastructure.{PetDontExist, PetExist, PetRepository}

class PetService(petRepository: PetRepository) {

  def create(pet: Pet): IO[Either[PetExist.type, Unit]] = {
    val exists: IO[Boolean] = petRepository.exist(pet.name)
    val create: IO[Unit] = petRepository.create(pet)

    val run: IO[Either[PetExist.type, Unit]] = exists.flatMap {
      case true => IO(PetExist.asLeft[Unit])
      case false => create.map(_ => ().asRight[PetExist.type])
    }

    run
  }

  def find(pet:Pet): IO[Option[Pet]] = {
    petRepository.findByName(pet.name)
  }

  def list: IO[List[Pet]] = {
    petRepository.list()
  }

  def update(newage: Int, pet: Pet): IO[Either[PetDontExist.type, Unit]] = {
    val exist: IO[Boolean] = petRepository.exist(pet.name)
    val update: IO[Unit] = petRepository.updateAge(newage, pet)

    val run: IO[Either[PetDontExist.type, Unit]] = exist.flatMap{
      // I can do
      // update.map(_ => ().asRight[PetDontExist.type])
      // Right(Unit))
      case true => update.map(_ => Right(Unit))
      // I can do:
      // PetDontExist.asLeft[Unit].pure[IO]
      // IO{PetDontExist.asLeft[Unit]}
      // IO(PetDontExist.asLeft[Unit])
      // IO(Left(PetDontExist.asLeft[Unit])
      // IO(Left(PetDontExist))
      case false => IO(Left(PetDontExist))
    }

    run
  }
}