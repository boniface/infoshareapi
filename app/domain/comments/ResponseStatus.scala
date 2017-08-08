package domain.comments

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/08/05.
  */
case class ResponseStatus(responseId: String,
                          status: String,
                          date: LocalDateTime)

object ResponseStatus {

  implicit val responseStatusId = Json.format[ResponseStatus]

  def identity: ResponseStatus = ResponseStatus("", "", LocalDateTime.now)
}
