package domain.person

import java.util.Date
import play.api.libs.json.Json


case class UserLanguage(id: String,
                        userId: String,
                        languageId: String,
                        reading: String,
                        writing: String,
                        speaking: String,
                        date: Date,
                        state: String)

object UserLanguage {
  implicit val personsLangFmt = Json.format[UserLanguage]

}
