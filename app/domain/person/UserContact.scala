package domain.person

import java.util.Date
import play.api.libs.json.Json


case class UserContact(id: String,
                       userId: String,
                       addressTypeId: String,
                       contactNumber: String,
                       status: String,
                       date: Date,
                       state: String)

object UserContact {
  implicit val personContactFmt = Json.format[UserContact]

}
