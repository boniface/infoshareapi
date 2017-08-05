package services.comments

import repositories.comments.CommentStatusRepository

/**
  * Created by hashcode
  */
trait CommentStatusService extends CommentStatusRepository {

}

object CommentStatusService extends CommentStatusService with CommentStatusRepository
