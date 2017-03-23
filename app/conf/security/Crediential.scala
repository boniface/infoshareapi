package conf.security

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/03/04.
  */
case class Crediential(email:String, password:String)

object Crediential {

  implicit val credentialFmt = Json.format[Crediential]

}
