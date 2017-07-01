package domain.security

import play.api.libs.json.Json

case class Roles(id: String, rolename: String)

object Roles {
  implicit val rolesFmt = Json.format[Roles]
  def identity: Roles = Roles("", RolesID.READER)
}
