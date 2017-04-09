package domain.demographics

import play.api.libs.json.Json


case class Gender(id:String, name:String, state:String)

object Gender{
  implicit val genderFmt = Json.format[Gender]
}