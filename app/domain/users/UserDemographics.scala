package domain.users

import java.time.LocalDateTime
import play.api.libs.json.Json

case class UserDemographics(emailId: String,
                            id: String,
                            genderId: String,
                            raceId: String,
                            dateOfBirth: LocalDateTime,
                            maritalStatusId: String,
                            numberOfDependencies: Int,
                            date: LocalDateTime,
                            state: String)

object UserDemographics {
  implicit val userDemoFmt = Json.format[UserDemographics]
  def identity: UserDemographics = UserDemographics("", "", "", "", LocalDateTime.now(), "", 0, LocalDateTime.now(), "")
}
