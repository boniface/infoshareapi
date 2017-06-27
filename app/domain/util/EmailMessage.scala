package domain.util

import play.api.libs.json.Json

case class EmailMessage(subject: String,
                        recipient: String,
                        from: String,
                        text: String,
                        html: String,
                        smtpConfig: SmtpConfig)

object EmailMessage {
  implicit val emMesg = Json.format[EmailMessage]
  def identity: EmailMessage = EmailMessage("", "", "", "", "", SmtpConfig.identity)
}
