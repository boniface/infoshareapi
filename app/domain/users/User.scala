package domain.users

import java.time.{LocalDateTime => Date}
import play.api.libs.json.Json

case class User(siteId: String,
                email: String,
                firstName: String,
                lastName: String,
                middleName: Option[String],
                screenName: Option[String],
                password: String,
                state: String,
                date: Date)

object User {
  implicit val userFmt = Json.format[User]
  def identity: User = User("", "", "", "", None, None, "", "", Date.now())

}
