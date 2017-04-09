package domain.util

import java.util.Date
import play.api.libs.json.Json

case class Mail( orgId:String,
                 id:String,
                 key:String,
                 value:String,
                 host:String,
                 port:String,
                 state:String,
                 date:Date)

object Mail{
  implicit val mailFmt = Json.format[Mail]


}
