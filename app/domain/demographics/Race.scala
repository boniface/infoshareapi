package domain.demographics

import play.api.libs.json.Json


case class Race(id:String, name:String, state:String)

object Race{
  implicit val raceFmt = Json.format[Race]
  def identity:Race =Race("", "", "")
}