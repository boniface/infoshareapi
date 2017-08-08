package domain.comments

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/08/05.
  */
case class Comment(siteId: String,
                   subjectId: String,
                   commentId: String,
                   comment: String,
                   emailId: String,
                   ipaddress: String,
                   date: LocalDateTime)

object Comment {
 implicit val commentFmt = Json.format[Comment]
 def identity:Comment = Comment("","","","","","",LocalDateTime.now)
}
