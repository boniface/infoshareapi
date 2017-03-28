package domain.person

import play.api.libs.json.Json


case class Person(org: String,
                  id: String,
                  firstName: String,
                  emailAddress: String,
                  lastName: String,
                  authvalue: String,
                  enabled: Boolean,
                  accountNonExpired: Boolean,
                  credentialsNonExpired: Boolean,
                  accountNonLocked: Boolean,
                  state: String
                 )

object Person {
  implicit val personFmt = Json.format[Person]

}
