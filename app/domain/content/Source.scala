package domain.content

import play.api.libs.json.Json


case class Source ( org: String,
                    id:String,
                    name:String,
                    description:String)

object Source {
  implicit val sourceFmt = Json.format[Source]

}
