package domain.users

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/09/21.
  */
case class Reputation(siteId:String, emailId:String, date:DateTime,value:Int)

object Reputation{
  implicit val reputationFmt = Json.format[Reputation]
}
