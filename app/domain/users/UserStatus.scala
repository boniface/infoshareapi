package domain.users

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 6/24/17.
  */
case class UserStatus(userId: String, date: LocalDateTime, status: String)

object UserStatus {
  implicit val userFmt = Json.format[UserStatus]
  def zero: UserStatus = UserStatus("", LocalDateTime.now(), "")
}
