package domain.util

import java.time.LocalDateTime
import play.api.libs.json.Json

case class ItemStatus(itemId: String,
                      date: LocalDateTime,
                      status: String,
                      description: String)

object ItemStatus {
  implicit val siteFmt = Json.format[ItemStatus]
  def identity: ItemStatus = ItemStatus("", LocalDateTime.now(), "", "")
}
