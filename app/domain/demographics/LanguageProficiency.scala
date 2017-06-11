package domain.demographics

import play.api.libs.json.Json

case class LanguageProficiency(id:String, name:String, state:String)

object LanguageProficiency{
  implicit val lanPFmt = Json.format[LanguageProficiency]
  def identity: LanguageProficiency = LanguageProficiency("", "", "")
}
