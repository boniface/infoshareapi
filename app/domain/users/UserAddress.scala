package domain.users

import java.time.LocalDateTime

import play.api.libs.json.Json

case class UserAddress(emailId: String,
                       id: String,
                       addressTypeId: String,
                       description: String,
                       postalCode: String,
                       date: LocalDateTime,
                       state: String)

object UserAddress {
  implicit val userAddressFmt = Json.format[UserAddress]
  def identity: UserAddress = UserAddress("", "", "", "", "", LocalDateTime.now(), "")
}
