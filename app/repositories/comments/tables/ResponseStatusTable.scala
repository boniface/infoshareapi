package repositories.comments.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.comments.ResponseStatus

import scala.concurrent.Future


abstract class ResponseStatusTable extends Table[ResponseStatusTable, ResponseStatus] {

  object responseId extends StringColumn with PartitionKey

  object date extends Col[LocalDateTime]  with PrimaryKey with ClusteringOrder with Ascending

  object status extends StringColumn

}

abstract class ResponseStatusTableImpl extends ResponseStatusTable with RootConnector {

  override lazy val tableName = "responseStatus"


  def save(responseStatus: ResponseStatus): Future[ResultSet] = {
    insert
      .value(_.responseId, responseStatus.responseId)
      .value(_.status, responseStatus.status)
      .value(_.date, responseStatus.date)
      .future()
  }

  def getResponseStatus(responseId: String): Future[Seq[ResponseStatus]] = {
    select
      .where(_.responseId eqs responseId)
      .fetchEnumerator() run Iteratee.collect()
  }

}

