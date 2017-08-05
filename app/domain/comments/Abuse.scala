package domain.comments

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/08/05.
  */
case class Abuse(siteId:String,
                 commentIdOrResponseId:String,
                 date:LocalDateTime,
                 details:String,
                 emailId:String)

object Abuse{
  implicit val abuseFmt =Json.format[Abuse]

  def identity:Abuse = Abuse("","",LocalDateTime.now(),"","")

}
