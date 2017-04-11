package domain.util

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/12/16.
  */
case class ItemStatus(itemId: String,
                      date: DateTime,
                      status: String,
                      description: String)

object ItemStatus {
  implicit val siteFmt = Json.format[ItemStatus]
}
