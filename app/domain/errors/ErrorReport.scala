package domain.errors

import java.util.Date

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/06.
 */
case class ErrorReport(zone:String,id:String,site:String,date:Date,message:String)

object ErrorReport {
  implicit val errorFmt = Json.format[ErrorReport]

}