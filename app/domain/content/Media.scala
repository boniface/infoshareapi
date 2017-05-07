package domain.content

import java.util.Date
import play.api.libs.json.Json

case class Media(contentId: String,
                 id: String,
                 description: String,
                 url: String,
                 mime: String,
                 date: Date,
                 state:String)

object Media{
  implicit val mediaFmt = Json.format[Media]
}
