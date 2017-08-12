package domain.content

import java.time.LocalDateTime
import play.api.libs.json.Json

case class PublishedContent(org: String,
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

object PublishedContent {
  implicit val publishedContentFmt = Json.format[PublishedContent]
  def identity: PublishedContent = PublishedContent("", "", LocalDateTime.now(), "", "", "", "", "", "", "", "")
}
