package domain.content

import play.api.libs.json.Json

case class ContentType(id: String, name: String, description: String)

object ContentType {
  implicit val contentTypeFmt = Json.format[ContentType]
  def identity: ContentType = ContentType("", "", "")
}
