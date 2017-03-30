package domain.person

import java.util.Date
import play.api.libs.json.Json


case class PersonLanguage(id: String,
                          personId: String,
                          languageId: String,
                          reading: String,
                          writing: String,
                          speaking: String,
                          date: Date,
                          state: String)

object PersonLanguage {
  implicit val personsLangFmt = Json.format[PersonLanguage]

}
