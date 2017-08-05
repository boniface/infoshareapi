package services.comments

import repositories.comments.CommentsRepository

/**
  * Created by hashcode
  */
trait CommentsService extends CommentsRepository {

}

object CommentsService extends CommentsService with CommentsRepository
