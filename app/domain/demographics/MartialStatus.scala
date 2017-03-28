package domain.demographics

import play.api.libs.json.Json

case class MartialStatus(id:String, name:String, state:String)

object MartialStatus{
  implicit val maritalStatusFmt = Json.format[MartialStatus]

}
