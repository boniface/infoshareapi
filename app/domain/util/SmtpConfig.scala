package domain.util

import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/10/05.
  */
case class SmtpConfig(port: Int = 587,
                      host: String = "smtp.gmail.com",
                      user: String,
                      password: String)

object SmtpConfig {

  implicit val smtpFmt = Json.format[SmtpConfig]

  def identity: SmtpConfig = SmtpConfig(0, "", "", "")
}
