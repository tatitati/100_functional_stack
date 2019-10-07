package learning.test.SeparateDataFromBehaviour.domain

import com.github.nscala_time.time.Imports.DateTime

final case class UserAccount (
   username: String,
   salt: String,
   hashedPassword: String,
   emailConfirmed: Boolean,
   registeredDateTime: DateTime
)
