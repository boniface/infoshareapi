package services.comments

import repositories.comments.{CommentStatusRepository, ResponseStatusRepository}

/**
  * Created by hashcode
  */
trait ResponseStatusService extends ResponseStatusRepository {

}

object ResponseStatusService extends ResponseStatusService with ResponseStatusRepository
