package domain.content

import java.time.{LocalDateTime => Date}
import play.api.libs.json.Json

case class EditedContent(org: String,
                         id: String,
                         dateCreated: Date,
                         creator: String,
                         source: String,
                         category: String,
                         title: String,
                         content: String,
                         contentType: String,
                         status: String,
                         state: String)

object EditedContent {
  implicit val editedContentFmt = Json.format[EditedContent]
  def identity: EditedContent = EditedContent("", "", Date.now(), "", "", "", "", "", "", "", "")
}
