package domain.person

import play.api.libs.json.Json


case class PersonRole(personId: String,
                       roleId: String,state:String
                       )

object PersonRole {
  implicit val personRoleFmt = Json.format[PersonRole]

}
