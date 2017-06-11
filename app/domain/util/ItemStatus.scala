package domain.util

import java.time.{LocalDateTime => Date}
import play.api.libs.json.Json


case class ItemStatus(itemId: String,
                      date: Date,
                      status: String,
                      description: String)

object ItemStatus {
  implicit val siteFmt = Json.format[ItemStatus]

  def identity:ItemStatus = ItemStatus("",Date.now(),"","")
}
