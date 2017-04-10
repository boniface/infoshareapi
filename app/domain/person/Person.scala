package domain.person

import play.api.libs.json.Json

/**
  * person or user model for user basic details
  * state refers to the state of person's account if its ACTIVE or DELETED etc
  */

case class Person(org: String,
                  id: String,
                  firstName: String,
                  lastName: String,
                  middleName: String,
                  emailAddress: String,
                  password: String,
                  is_enabled: Boolean,
                  accountNonExpired: Boolean,
                  credentialsNonExpired: Boolean,
                  accountNonLocked: Boolean,
                  state: String
                 )

object Person {
  implicit val personFmt = Json.format[Person]

}
