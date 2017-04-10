package domain.person

import java.util.Date
import play.api.libs.json.Json


case class PersonContact(id: String,
                         personId: String,
                         addressTypeId: String,
                         contactNumber: String,
                         status: String,
                         date: Date,
                         state: String)

object PersonContact {
  implicit val personContactFmt = Json.format[PersonContact]

}
