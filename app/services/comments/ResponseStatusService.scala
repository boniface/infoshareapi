package services.comments

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.comments.ResponseStatus
import repositories.comments.ResponseStatusRepository

import scala.concurrent.Future

/**
  * Created by hashcode
  */
trait ResponseStatusService extends ResponseStatusRepository {

  def save(obj: ResponseStatus): Future[ResultSet] = {
    database.responseStatusTable.save(obj)
  }

  def getResponseStatus(responseId: String): Future[Seq[ResponseStatus]] = {
    database.responseStatusTable.getResponseStatus(responseId)
  }
}

@Singleton
object ResponseStatusService extends ResponseStatusService with ResponseStatusRepository
