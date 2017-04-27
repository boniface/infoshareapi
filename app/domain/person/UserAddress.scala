package domain.person

import java.util.Date

import play.api.libs.json.Json


case class UserAddress(id: String,
                       userId: String,
                       description: String,
                       postalCode: String,
                       addressTypeId: String,
                       date: Date,
                       state: String )

object UserAddress {
  implicit val personAddressFmt = Json.format[UserAddress]

}
