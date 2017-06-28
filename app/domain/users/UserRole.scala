package domain.users

import java.time.LocalDateTime

import play.api.libs.json.Json

case class UserRole(emailId: String,
                    date:LocalDateTime,
                    roleId: String)
object UserRole {
  implicit val userroleFmt = Json.format[UserRole]
  def zero:UserRole = UserRole("",LocalDateTime.now(),"")
}
