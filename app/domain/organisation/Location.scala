package domain.organisation

import java.util.Date

import play.api.libs.json.Json


case class Location(org:String,
                     id:String,
                     name:String,
                     locationTypeId:String,
                     code:String,
                     latitude:String,
                     longitude:String,
                     parentId:String,
                     state:String,
                     date:Date)

object Location{
  implicit val location = Json.format[Location]

}
