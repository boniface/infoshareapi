package domain.person

import java.util.Date

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/11/10.
 */
case class PersonDemographics(id: String,
                              personId: String,
                              genderId: String,
                              raceId: String,
                              dateOfBirth: Date,
                              maritalStatusId: String,
                              numberOfDependencies: Int,
                              date: Date,
                              state:String)

object PersonDemographics {
  implicit val personDemoFmt = Json.format[PersonDemographics]

}
