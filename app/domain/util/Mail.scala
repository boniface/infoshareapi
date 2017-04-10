package domain.util

import java.util.Date

import play.api.libs.json.Json

/**
  * Created by kuminga on 2016/09/01.
  */
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
}
