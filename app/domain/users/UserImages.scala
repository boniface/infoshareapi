package domain.users

import java.time.{LocalDateTime => Date}
import play.api.libs.json.Json

case class UserImages(org: String,
                      emailId: String,
                      id: String,
                      url: String,
                      description: String,
                      mime: String,
                      size: Option[String],
                      date: Date)

object UserImages {
  implicit val companyImagesFmt = Json.format[UserImages]
  def identity: UserImages = UserImages("", "", "", "", "", "", None, Date.now())
}
