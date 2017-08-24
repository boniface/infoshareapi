package repositories.comments.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.comments.Response

import scala.concurrent.Future


abstract class ResponseTable extends Table[ResponseTable, Response] {

  object commentId extends StringColumn with PartitionKey

  object responseId extends StringColumn with PrimaryKey

  object response extends StringColumn

  object emailId extends StringColumn

  object ipaddress extends StringColumn

  object date extends Col[LocalDateTime]


}

abstract class ResponseTableImpl extends ResponseTable with RootConnector {

  override lazy val tableName = "response"


  def save(response: Response): Future[ResultSet] = {
    insert
      .value(_.commentId, response.commentId)
      .value(_.responseId, response.responseId)
      .value(_.response, response.response)
      .value(_.emailId, response.emailId)
      .value(_.ipaddress, response.ipaddress)
      .value(_.date, response.date)
      .future()
  }

  def getCommentResponses(commentId: String): Future[Seq[Response]] = {
    select
      .where(_.commentId eqs commentId)
      .fetchEnumerator() run Iteratee.collect()
  }


  def getResponse(commentId: String, responseId: String): Future[Option[Response]] = {
    select
      .where(_.commentId eqs commentId)
      .and(_.responseId eqs responseId)
      .one()
  }
}

abstract class ResponseByUserTable extends Table[ResponseByUserTable, Response] {

  object emailId extends StringColumn with PartitionKey

  object responseId extends StringColumn with PrimaryKey

  object commentId extends StringColumn

  object response extends StringColumn

  object ipaddress extends StringColumn

  object date extends Col[LocalDateTime]


}

abstract class ResponseByUserTableImpl extends ResponseByUserTable with RootConnector {

  override lazy val tableName = "userresponse"


  def save(response: Response): Future[ResultSet] = {
    insert
      .value(_.commentId, response.commentId)
      .value(_.responseId, response.responseId)
      .value(_.response, response.response)
      .value(_.emailId, response.emailId)
      .value(_.ipaddress, response.ipaddress)
      .value(_.date, response.date)
      .future()
  }

  def getUserResponses(emailId: String): Future[Seq[Response]] = {
    select
      .where(_.emailId eqs emailId)
      .fetchEnumerator() run Iteratee.collect()
  }
}

abstract class SingleResponseTable extends Table[SingleResponseTable, Response] {

  object responseId extends StringColumn with PartitionKey

  object commentId extends StringColumn with PrimaryKey

  object response extends StringColumn

  object emailId extends StringColumn

  object ipaddress extends StringColumn

  object date extends Col[LocalDateTime]

}

abstract class SingleResponseTableImpl extends SingleResponseTable with RootConnector {

  override lazy val tableName = "singleresponse"

  def save(response: Response): Future[ResultSet] = {
    insert
      .value(_.commentId, response.commentId)
      .value(_.responseId, response.responseId)
      .value(_.response, response.response)
      .value(_.emailId, response.emailId)
      .value(_.ipaddress, response.ipaddress)
      .value(_.date, response.date)
      .future()
  }

  def getResponse(responseId: String): Future[Option[Response]] = {
    select
      .where(_.responseId eqs responseId)
      .one()
  }
}


