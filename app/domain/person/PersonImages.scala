package domain.person

import java.util.Date

import play.api.libs.json.Json


case class PersonImages(org: String,
                        personId: String,
                        id: String,
                        url: String,
                        description: String,
                        mime: String,
                        size: Option[String],
                        date: Date)

object PersonImages {
  implicit val companyImagesFmt = Json.format[PersonImages]

}
