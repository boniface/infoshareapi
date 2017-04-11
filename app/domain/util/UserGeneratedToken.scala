package domain.util

import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/12/23.
  */
case class UserGeneratedToken(token: String,
                              status: String,
                              message: String,
                              siteId: String)

object UserGeneratedToken {
  implicit val tokenForm = Json.format[UserGeneratedToken]
}