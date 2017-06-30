package domain.users

import java.time.LocalDateTime
import play.api.libs.json.Json

case class UserContact(emailId: String,
                       id: String,
                       addressTypeId: String,
                       contactNumber: String,
                       date: LocalDateTime,
                       state: String)

object UserContact {
  implicit val userContactFmt = Json.format[UserContact]
  def identity: UserContact = UserContact("", "", "", "", LocalDateTime.now(), "")

}
