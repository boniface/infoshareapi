package domain.person

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * person or user model for user basic details
  * state refers to the state of person's account if its ACTIVE or DELETED etc
  */

case class User(org: String,
                email: String,
                firstName: String,
                lastName: String,
                middleName: Option[String],
                screenName: Option[String],
                password: String,
                state: String,
                date: DateTime)

object User {
  implicit val personFmt = Json.format[User]
  def default:User= User("","","","",None,None,"","", new DateTime())

}
