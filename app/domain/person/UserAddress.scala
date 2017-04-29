package domain.person

import java.util.Date

import play.api.libs.json.Json


case class UserAddress(userId: String,
                       id: String,
                       addressTypeId: String,
                       description: String,
                       postalCode: String,
                       date: Date,
                       state: String )

object UserAddress {
  implicit val userAddressFmt = Json.format[UserAddress]

}
