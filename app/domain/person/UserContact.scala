package domain.person

import java.util.Date
import play.api.libs.json.Json


case class UserContact(userId: String,
                       id: String,
                       addressTypeId: String,
                       contactNumber: String,
                       date: Date,
                       state: String)

object UserContact {
  implicit val userContactFmt = Json.format[UserContact]

}
