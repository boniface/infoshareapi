package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.Reputation

import scala.concurrent.Future

/**
 * Created by Rosie on 2016/11/28.
 */
class ReputationTable extends CassandraTable[ReputationTable, Reputation]{

  object siteId extends StringColumn(this) with PartitionKey
  object emailId extends StringColumn(this) with PrimaryKey
  object date extends DateTimeColumn(this) with PrimaryKey
  object value extends IntColumn(this)
}

abstract class  ReputationTableImpl extends ReputationTable with RootConnector {

  override lazy val tableName = "reputation"

  def save(reputation: Reputation): Future[ResultSet] = {
    insert
      .value(_.siteId, reputation.siteId)
      .value(_.emailId, reputation.emailId)
      .value(_.date, reputation.date)
      .value(_.value, reputation.value)
      .future()
  }


  def getDayReputation(siteId: String, emailId:String, date:DateTime): Future[Option[Reputation]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.emailId eqs emailId)
      .and(_.date eqs date)
      .one()
  }


  def getUserReputations(siteId: String, emailId:String): Future[Seq[Reputation]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.emailId eqs emailId)
      .fetchEnumerator() run Iteratee.collect()
  }
}
