package domain.person

import java.util.Date

import play.api.libs.json.Json


case class PersonAddress( id: String,
                          personId: String,
                          description: String,
                          postalCode: String,
                          addressTypeId: String,
                          date: Date,
                          state: String
                          )

object PersonAddress {
  implicit val personAddressFmt = Json.format[PersonAddress]

}
