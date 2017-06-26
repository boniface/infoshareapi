package domain.users

import java.time.LocalDateTime
import play.api.libs.json.Json

case class User(siteId: String,
                email: String,
                firstName:  Option[String],
                lastName:  Option[String],
                middleName: Option[String],
                screenName: String,
                password: String,
                state: String,
                date: LocalDateTime)

object User {

  implicit val userFmt = Json.format[User]

  def identity: User = User("", "", None, None, None, "", "", "", LocalDateTime.now())

}
