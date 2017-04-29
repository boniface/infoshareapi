package domain.person

import play.api.libs.json.Json


case class UserRole(userId: String, roleId: String, state:String)

object UserRole {
  implicit val userRoleFmt = Json.format[UserRole]

}
