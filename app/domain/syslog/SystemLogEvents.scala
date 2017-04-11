package domain.syslog

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/08/12.
  */
case class SystemLogEvents(siteId: String,
                           id: String,
                           eventName: String,
                           eventType: String,
                           message: String,
                           date: DateTime)

object SystemLogEvents {
  implicit val syseventLog = Json.format[SystemLogEvents]
}
