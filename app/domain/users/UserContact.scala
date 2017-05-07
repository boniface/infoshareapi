package domain.users

import java.util.Date
import play.api.libs.json.Json


case class UserContact(emailId: String,
                       id: String,
                       addressTypeId: String,
                       contactNumber: String,
                       date: Date,
                       state: String)

object UserContact {
  implicit val userContactFmt = Json.format[UserContact]

}
