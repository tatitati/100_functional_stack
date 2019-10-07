package learning.test.SeparateDataFromBehaviour.builders

import learning.test.SeparateDataFromBehaviour.domain.{User, UserAccount, UserProfile}

case class BuildUser(
  userProfile: UserProfile = BuildUserProfile().build(),
  userAccount: UserAccount = BuildUserAccount().build()
) extends Buildable[User] {

  def withUserProfile(withUserProfile: UserProfile) = copy(userProfile = withUserProfile)
  def withUserAccount(withUserAccount: UserAccount) = copy(userAccount = withUserAccount)

  def build(): User = {
    User(
      profile = userProfile,
      account = userAccount
    )
  }
}
