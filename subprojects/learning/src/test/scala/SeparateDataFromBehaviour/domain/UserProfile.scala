package learning.test.SeparateDataFromBehaviour.domain

import com.github.nscala_time.time.Imports.DateTime

final case class UserProfile(
  firstname: String,
  surname: String,
  datebirth: Option[DateTime],
  email: String
)