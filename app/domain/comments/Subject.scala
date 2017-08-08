package domain.comments

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/08/05.
  */
case class Subject(siteId: String,
                   subjectId: String,
                   name: String,
                   url: String,
                   date: LocalDateTime)

object Subject {
  implicit val subjectFmt = Json.format[Subject]

  def identity: Subject = Subject("", "", "", "", LocalDateTime.now())

}
