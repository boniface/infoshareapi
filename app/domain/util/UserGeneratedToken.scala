package domain.util

import play.api.libs.json.Json


case class UserGeneratedToken(token: String,
                              status: String,
                              message: String,
                              siteId: String)

object UserGeneratedToken {
  implicit val tokenForm = Json.format[UserGeneratedToken]

  def identity: UserGeneratedToken = UserGeneratedToken("", "", "", "")
}