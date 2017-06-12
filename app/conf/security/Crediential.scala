package conf.security

import play.api.libs.json.Json

case class Crediential(email: String, password: String)

object Crediential {

  implicit val credentialFmt = Json.format[Crediential]

}
