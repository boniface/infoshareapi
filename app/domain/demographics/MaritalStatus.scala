package domain.demographics

import play.api.libs.json.Json

case class MaritalStatus(id: String, name: String, state: String)

object MaritalStatus {
  implicit val maritalStatusFmt = Json.format[MaritalStatus]
  def identity: MaritalStatus = MaritalStatus("", "", "")

}
