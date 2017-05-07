package domain.content

import java.util.Date
import play.api.libs.json.Json


case class RawContent(org: String,
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

object RawContent {
  implicit val rawContentFmt = Json.format[RawContent]

}