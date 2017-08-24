package services.comments

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.comments.Comment
import repositories.comments.CommentsRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by hashcode
  */
trait CommentsService extends CommentsRepository {

  def save(obj: Comment):  Future[ResultSet] = {
    for {
      entity <- database.commentsTable.save(obj)
      entity <- database.commentsByUserTable.save(obj)
      entity <- database.singleCommentsTable.save(obj)
    } yield entity
  }

  def getSiteComments(siteId: String): Future[Seq[Comment]] = {
    database.commentsTable.getSiteComments(siteId)
  }

  def getSubjectComments(map: Map[String, String]): Future[Seq[Comment]] = {
    database.commentsTable.getSubjectComments(map)
  }

  def getComment(map: Map[String, String]): Future[Option[Comment]] = {
    database.commentsTable.getComment(map)
  }

  def getUserComments(map: Map[String, String]): Future[Seq[Comment]] = {
    database.commentsByUserTable.getUserComments(map)
  }

  def getComment(commentId: String): Future[Option[Comment]] = {
    database.singleCommentsTable.getComment(commentId)
  }
}

@Singleton
object CommentsService extends CommentsService with CommentsRepository
