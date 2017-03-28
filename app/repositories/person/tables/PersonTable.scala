package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.Person

import scala.concurrent.Future


class PersonTable extends CassandraTable[PersonTable, Person]{

  object siteId extends StringColumn(this) with PartitionKey
  object emailId extends StringColumn(this) with PrimaryKey
  object date extends DateTimeColumn(this) with PrimaryKey
  object value extends IntColumn(this)
}

abstract class  PersonTableImpl extends PersonTable with RootConnector {

  override lazy val tableName = "reputation"

  def save(reputation: Person): Future[ResultSet] = {
    insert
      .value(_.siteId, reputation.siteId)
      .value(_.emailId, reputation.emailId)
      .value(_.date, reputation.date)
      .value(_.value, reputation.value)
      .future()
  }


  def getDayReputation(siteId: String, emailId:String, date:DateTime): Future[Option[Person]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.emailId eqs emailId)
      .and(_.date eqs date)
      .one()
  }


  def getUserReputations(siteId: String, emailId:String): Future[Seq[Person]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.emailId eqs emailId)
      .fetchEnumerator() run Iteratee.collect()
  }
}

