package domain.votes

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/08/05.
  */
case class VoteUp(
                   siteId: String,
                   itemId: String,
                   ipAddress: String,
                   itemOwnerId: String,
                   date: LocalDateTime)

object VoteUp {
  implicit val voteUpFmt = Json.format[VoteUp]

  def identity: VoteUp = VoteUp("", "", "", "", LocalDateTime.now())
}
