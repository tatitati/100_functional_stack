package SeparateDataFromBehaviour.builders

import SeparateDataFromBehaviour.domain.UserProfile
import com.github.nscala_time.time.Imports.DateTime

case class BuildUserProfile(
  firstname: String = Faker.text(),
  surname: String = Faker.text(),
  datebirth: Option[DateTime] = Faker.anyOf(None, Some(Faker.date)),
  email: String = Faker.text()
 ) extends Buildable[UserProfile] {

  def withFirstName(withFirstane: String) = copy(firstname = withFirstane)
  def withSurname(withSurname: String) = copy(surname = withSurname)
  def withDateBirth(withDateBirth: Option[DateTime]) = copy(datebirth = withDateBirth)
  def withEmail(withEmail: String) = copy(email = withEmail)

  def build(): UserProfile = {
    UserProfile(
      firstname,
      surname,
      datebirth,
      email
    )
  }
}
