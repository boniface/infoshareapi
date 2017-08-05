package domain.comments

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/08/05.
  */
case class Response(commentId:String,
                    responseId:String,
                    response:String,
                    emailId:String,
                    ipaddress:String,
                    date:LocalDateTime)

object Response{
  implicit val response =Json.format[Response]

  def identity:Response=Response("","","","","",LocalDateTime.now)
}
