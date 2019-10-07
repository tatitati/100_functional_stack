package SeparateDataFromBehaviour.builders

import SeparateDataFromBehaviour.domain.UserAccount
import com.github.nscala_time.time.Imports.DateTime

case class BuildUserAccount(
   username: String = Faker.text(),
   salt: String = Faker.text(),
   hashedPassword: String = Faker.text(),
   emailConfirmed: Boolean = Faker.boolean,
   registeredDateTime: DateTime = Faker.date
 ) extends Buildable[UserAccount]{

  def withUsername(withUserName: String)= copy(username = withUserName)
  def withSalt(withSalt: String)= copy(salt = withSalt)
  def withHashedPassword(withHashedPassword: String)= copy(hashedPassword = withHashedPassword)
  def withEmailConfirmed(withEmailConfirmed: Boolean)= copy(emailConfirmed = withEmailConfirmed)
  def withRegisteredDateTime(withRegisteredDateTime: DateTime)= copy(registeredDateTime = withRegisteredDateTime)

  override def build(): UserAccount = {
    UserAccount(
        username = username,
        salt = salt,
        hashedPassword = hashedPassword,
        emailConfirmed = emailConfirmed,
        registeredDateTime = registeredDateTime
    )
  }
}
