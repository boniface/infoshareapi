package domain.content

import java.time.LocalDateTime
import play.api.libs.json.Json

case class RawContent(org: String,
                      id: String,
                      dateCreated: LocalDateTime,
                      creator: String,
                      source: String,
                      category: String,
                      title: String,
                      content: String,
                      contentTypeId: String,
                      status: String,
                      state: String)

object RawContent {
  implicit val rawContentFmt = Json.format[RawContent]
  def identity: RawContent = RawContent("", "", LocalDateTime.now(), "", "", "", "", "", "", "", "")
}
