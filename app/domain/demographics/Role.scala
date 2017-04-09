package domain.demographics

import play.api.libs.json.Json

case class Role(id: String,
                name: String,
                description: String,
                state:String)
object Role {
  implicit val roleFmt = Json.format[Role]
}
