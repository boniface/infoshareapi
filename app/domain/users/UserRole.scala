package domain.users

import play.api.libs.json.Json


case class UserRole(emailId: String, roleId: String, state:String)

object UserRole {
  implicit val userRoleFmt = Json.format[UserRole]
  def identity: UserRole = UserRole("", "" ,"")
}
