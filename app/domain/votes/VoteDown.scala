package domain.votes

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/05/11.
 */
case class VoteDown(siteId:String,itemId:String,ipAddress:String,itemOwnerId:String, date:DateTime)

object VoteDown {
  implicit val commentFmt = Json.format[VoteDown]
}