package domain.organisation

import java.time.LocalDateTime
import play.api.libs.json.Json

case class OrganisationLogo(org: String,
                            id: String,
                            url: String,
                            size: Option[String],
                            description: String,
                            mime: String,
                            date: LocalDateTime)

object OrganisationLogo {
  implicit val orgLogoFmt = Json.format[OrganisationLogo]
  def identity: OrganisationLogo = OrganisationLogo("", "", "", None, "", "", LocalDateTime.now())
}
