package learning.test.SeparateDataFromBehaviour

import learning.test.SeparateDataFromBehaviour.builders.{BuildUser, BuildUserProfile}
import learning.test.SeparateDataFromBehaviour.domain.UserId
import org.scalatest.FunSuite

class UserSpec extends FunSuite {

  test("UserOp.getUserId()") {
    val user = BuildUser().build()
    val userId = user.getUserId()

    assert(userId.isInstanceOf[UserId])
  }

  test("UserOp.updateEmail(...)") {
    val profile = BuildUserProfile().withEmail("email1").build()
    val user1 = BuildUser()
      .withUserProfile(profile)
      .build()

    assert("email1" === user1.profile.email)

    val user11 = user1.updateEmail(user1, "email2")

    assert("email2" === user11.profile.email)
  }

  test("UserOp.setSurrogateId(...)") {
    val user = BuildUser().build()
    val userUpdated = user.setSurrogateId(Some(232323L))
  }
}
