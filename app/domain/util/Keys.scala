package domain.util

import play.api.libs.json.Json

/**
  * Created by kuminga on 2016/09/01.
  */
case class Keys (id:String,value:String,status:String)

object Keys{
  implicit val keysFmt = Json.format[Keys]
}
