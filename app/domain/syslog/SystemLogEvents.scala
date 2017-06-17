package domain.syslog

import java.time.{LocalDateTime => Date}
import play.api.libs.json.Json

case class SystemLogEvents(org: String,
                           id: String,
                           eventName: String,
                           eventType: String,
                           message: String,
                           date: Date)

object SystemLogEvents {
  implicit val syseventLog = Json.format[SystemLogEvents]
  def identity: SystemLogEvents = SystemLogEvents("", "", "", "", "", Date.now())
}
