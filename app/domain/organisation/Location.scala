package domain.organisation

import java.time.LocalDateTime
import play.api.libs.json.Json

case class Location(org: String,
                    id: String,
                    name: String,
                    locationTypeId: String,
                    code: String,
                    latitude: String,
                    longitude: String,
                    parentId: String,
                    state: String,
                    date: LocalDateTime)

object Location {
  implicit val location = Json.format[Location]
  def identity: Location = Location("", "", "", "", "", "", "", "", "", LocalDateTime.now())
}
