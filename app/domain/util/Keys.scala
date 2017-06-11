package domain.util

import play.api.libs.json.Json

case class Keys (id:String,value:String,status:String)

object Keys{
  implicit val keysFmt = Json.format[Keys]
  def identity:Keys = Keys("","","")
}

