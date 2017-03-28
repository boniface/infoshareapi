package domain.storage

import play.api.libs.json.Json


case class FileResults(
                        id: String,
                        url: String,
                        size: Option[String],
                        mime:String
                        )


object FileResults {

  implicit val advertFmt = Json.format[FileResults]

}
