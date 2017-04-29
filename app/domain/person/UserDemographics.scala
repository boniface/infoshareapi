package domain.person

import java.util.Date

import play.api.libs.json.Json


case class UserDemographics(userId: String,
                            id: String,
                            genderId: String,
                            raceId: String,
                            dateOfBirth: Date,
                            maritalStatusId: String,
                            numberOfDependencies: Int,
                            date: Date,
                            state:String)

object UserDemographics {
  implicit val userDemoFmt = Json.format[UserDemographics]

}
