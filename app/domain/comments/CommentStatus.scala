package domain.comments

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  * Created by hashcode on 2017/08/05.
  */
case class CommentStatus(commentId:String,
                         status:String,
                         date:LocalDateTime
                        )

object CommentStatus{
  implicit val commentStatus = Json.format[CommentStatus]

  def identity:CommentStatus=CommentStatus("","",LocalDateTime.now)

}
