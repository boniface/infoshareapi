package domain.demographics

import play.api.libs.json.Json


case class Language(id:String, name:String, state:String)

object Language{
  implicit val langFmt = Json.format[Language]
}
