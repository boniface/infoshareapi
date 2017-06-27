package domain.util

import play.api.libs.json.Json

case class SmtpConfig(port: Int = 587,
                      host: String = "smtp.gmail.com",
                      user: String,
                      password: String)

object SmtpConfig {
  implicit val smtpFmt = Json.format[SmtpConfig]
  def identity: SmtpConfig = SmtpConfig(0, "", "", "")
}
