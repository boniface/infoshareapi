package domain.sites

import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/09/21.
  */
case class Administrators(siteId: String, emailId: String)

object Administrators {

  implicit val administratorsFmt = Json.format[Administrators]
}
