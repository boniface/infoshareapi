package domain.content

import java.time.LocalDateTime
import play.api.libs.json.Json

case class EditedContent(org: String,
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

object EditedContent {
  implicit val editedContentFmt = Json.format[EditedContent]
  def identity: EditedContent = EditedContent("", "", LocalDateTime.now(), "", "", "", "", "", "", "", "")
}
