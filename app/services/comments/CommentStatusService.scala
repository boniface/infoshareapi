package services.comments

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.comments.CommentStatus
import repositories.comments.CommentStatusRepository

import scala.concurrent.Future

trait CommentStatusService extends CommentStatusRepository {

  def save(obj: CommentStatus): Future[ResultSet] = {
    database.commentStatusTable.save(obj)
  }

  def getCommentStatus(commentsId: String): Future[Seq[CommentStatus]] = {
    database.commentStatusTable.getCommentStatus(commentsId)
  }
}

@Singleton
object CommentStatusService extends CommentStatusService with CommentStatusRepository
