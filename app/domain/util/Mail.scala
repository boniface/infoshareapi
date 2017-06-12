package domain.util

import java.time.{LocalDateTime => Date}
import play.api.libs.json.Json

case class Mail(siteId: String,
                id: String,
                key: String,
                value: String,
                host: String,
                port: String,
                state: String,
                date: Date)

object Mail {
  implicit val mailFmt = Json.format[Mail]

  def identity: Mail = Mail("", "", "", "", "", "", "", Date.now())
}
