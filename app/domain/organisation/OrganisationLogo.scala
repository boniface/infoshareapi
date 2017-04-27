package domain.organisation

import java.util.Date

import play.api.libs.json.Json


case class OrganisationLogo(org: String,
                            id: String,
                            url: String,
                            size: Option[String],
                            description:String,
                            mime: String,
                            date: Date)

object OrganisationLogo {
  implicit val orgLogoFmt = Json.format[OrganisationLogo]
}
