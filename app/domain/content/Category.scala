package domain.content

import play.api.libs.json.Json

case class Category(id: String, name: String, description: String)

object Category {
  implicit val categoryFmt = Json.format[Category]
  def identity: Category = Category("", "", "")
}
