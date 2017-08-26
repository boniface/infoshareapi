package domain.votes

import java.time.LocalDateTime

import play.api.libs.json.Json

case class Vote(siteId: String,
                itemId: String,
                ipAddress: String,
                itemOwnerId: String,
                date: LocalDateTime,
                status: String)

object Vote {
  implicit val voteFmt = Json.format[Vote]
  def identity: Vote= Vote("", "", "", "", LocalDateTime.now(), "")
}
