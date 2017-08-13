package services.comments

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.comments.Response
import repositories.comments.ResponseRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ResponseService extends ResponseRepository {

  def save(obj: Response): Future[ResultSet] = {
    for {
      entity <- database.responseTable.save(obj)
      entity <- database.responseByUserTable.save(obj)
      entity <- database.singleResponseTable.save(obj)
    } yield entity
  }

  def getCommentResponses(commentId: String): Future[Seq[Response]] = {
    database.responseTable.getCommentResponses(commentId)
  }

  def getResponse(commentId: String, responseId: String): Future[Option[Response]] = {
    database.responseTable.getResponse(commentId, responseId)
  }

  def getUserResponses(emailId: String): Future[Seq[Response]] = {
    database.responseByUserTable.getUserResponses(emailId)
  }

  def getResponse(responseId: String): Future[Option[Response]] = {
    database.singleResponseTable.getResponse(responseId)
  }
}

@Singleton
object ResponseService extends ResponseService with ResponseRepository
