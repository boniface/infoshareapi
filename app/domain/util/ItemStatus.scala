package domain.util

import java.time.LocalDateTime

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/12/16.
  */
case class ItemStatus(itemId: String,
                      date: LocalDateTime,
                      status: String,
                      description: String)

object ItemStatus {
  implicit val siteFmt = Json.format[ItemStatus]

  def identity:ItemStatus = ItemStatus("",LocalDateTime.now(),"","")
}
