package domain.person

import java.util.Date
import play.api.libs.json.Json


case class PersonContact(id: String,
                         personId: String,
                         addressTypeId: String,
                         contactValue: String,
                         status: String,
                         date: Date,
                         state: String)

object PersonContact {
  implicit val personcontactFmt = Json.format[PersonContact]

}
