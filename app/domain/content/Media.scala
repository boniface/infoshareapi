package domain.content

import java.time.LocalDateTime
import play.api.libs.json.Json

case class Media(contentId: String,
                 id: String,
                 description: String,
                 url: String,
                 mime: String,
                 date: LocalDateTime,
                 state: String)

object Media {
  implicit val mediaFmt = Json.format[Media]
  def identity: Media = Media("", "", "", "", "", LocalDateTime.now(), "")
}
