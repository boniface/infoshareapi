package domain.users

import java.time.{LocalDateTime =>Date}
import play.api.libs.json.Json


case class UserLanguage(emailId: String,
                        id: String,
                        languageId: String,
                        reading: String,
                        writing: String,
                        speaking: String,
                        date: Date,
                        state: String)

object UserLanguage {
  implicit val usersLangFmt = Json.format[UserLanguage]
  def identity: UserLanguage = UserLanguage("", "", "", "", "", "", Date.now(), "")
}
