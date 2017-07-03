package domain.users

import java.time.LocalDateTime

import domain.security.RolesID
import play.api.libs.json.{Json, OFormat}

case class UserRole(siteId: String,
                    emailId: String,
                    date: LocalDateTime,
                    roleId: String)

object UserRole {
  implicit val userroleFmt: OFormat[UserRole] = Json.format[UserRole]

  def zero: UserRole = UserRole(" "," ", LocalDateTime.now(), RolesID.READER)
}
