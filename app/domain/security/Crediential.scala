package domain.security

import play.api.libs.json.Json

case class Credential(email:String, password:String, siteId:String)

object Credential {

  implicit val credentialFmt = Json.format[Credential]
  def identity:Credential = Credential("","","")

}
