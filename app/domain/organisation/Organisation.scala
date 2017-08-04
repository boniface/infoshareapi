package domain.organisation

import java.time.LocalDateTime
import play.api.libs.json.Json

case class Organisation(id: String,
                        name: String,
                        details: Map[String, String],
                        adminAttached: String,
                        date: LocalDateTime,
                        state: String)

object Organisation {
  implicit val companyFmt = Json.format[Organisation]
  def identity: Organisation = Organisation("", "", Map(), "", LocalDateTime.now(), "")
}
