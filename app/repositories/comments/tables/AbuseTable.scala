package repositories.comments.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.comments.Abuse

import scala.concurrent.Future

/**
  * Created by hashcode
  */

abstract class AbuseTable  extends Table[AbuseTable, Abuse]{

  object siteId extends StringColumn with PartitionKey
  object commentIdOrResponseId extends StringColumn with PrimaryKey
  object emailId extends StringColumn with PrimaryKey
  object date extends Col[LocalDateTime]  with PrimaryKey
  object details extends StringColumn

}

abstract class  AbuseTableImpl extends AbuseTable with RootConnector {

  override lazy val tableName = "abuse"

  def save(abuse: Abuse): Future[ResultSet] = {
    insert
      .value(_.siteId, abuse.siteId)
      .value(_.commentIdOrResponseId,abuse.commentIdOrResponseId)
      .value(_.details, abuse.details)
      .value(_.emailId, abuse.emailId)
      .value(_.date, abuse.date)
      .future()
  }

  def getItemAbuse(siteId: String, commentIdOrResponseId: String): Future[Seq[Abuse]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.commentIdOrResponseId eqs commentIdOrResponseId)
      .fetchEnumerator() run Iteratee.collect()
  }
}

abstract class AbuseByUserTable extends Table[AbuseByUserTable, Abuse]{

  object siteId extends StringColumn with PartitionKey
  object emailId extends StringColumn with PrimaryKey
  object commentIdOrResponseId extends StringColumn with PrimaryKey
  object date extends Col[LocalDateTime]  with PrimaryKey
  object details extends StringColumn

}

abstract class  AbuseByUserTableImpl extends AbuseByUserTable with RootConnector {
  override lazy val tableName = "userabuse"

  def save(abuse: Abuse): Future[ResultSet] = {
    insert
      .value(_.siteId, abuse.siteId)
      .value(_.commentIdOrResponseId,abuse.commentIdOrResponseId)
      .value(_.details, abuse.details)
      .value(_.emailId, abuse.emailId)
      .value(_.date, abuse.date)
      .future()
  }

  def getUserAbusiveComments(siteId: String,emailId:String): Future[Seq[Abuse]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.emailId eqs emailId)
      .fetchEnumerator() run Iteratee.collect()
  }
}

