package domain.security

import play.api.libs.json.Json

case class Token(id: String, tokenValue: String)

object Token {
  implicit val tokenFmt = Json.format[Token]
  def identity: Token = Token("", "")
}
