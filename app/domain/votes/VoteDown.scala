package domain.votes

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
 * Created by hashcode on 2017/08/05.
 */
case class VoteDown(siteId:String,itemId:String,ipAddress:String,itemOwnerId:String, date:LocalDateTime)

object VoteDown {
  implicit val commentFmt = Json.format[VoteDown]

  def identity: VoteDown = VoteDown("", "", "", "", LocalDateTime.now())
}
