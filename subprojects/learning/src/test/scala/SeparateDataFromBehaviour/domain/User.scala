package learning.test.SeparateDataFromBehaviour.domain

final case class User(
   profile: UserProfile,
   account: UserAccount,
   surrogateId: Option[Long] = None
 ) {
  def getUserId(): UserId = UserId(profile.surname)

  def updateEmail(user: User, newEmail: String): User = {
    this.copy(
      profile = profile.copy(
        email = newEmail
      )
    )
  }

  def updateSurname(withSurname: String): User = {
    this.copy(
      profile = profile.copy(
        surname = withSurname
      )
    )
  }

  def setSurrogateId(withSurrogateId: Option[Long]): Either[ErrorSettingSurrogateId, User] = {
    if(surrogateId != None) Left(SurrogateIdAlreadySet)
    else if(withSurrogateId == None) Left(SurrogateIdCannotSetToNone)
    else Right(copy(surrogateId = withSurrogateId))
  }
}



