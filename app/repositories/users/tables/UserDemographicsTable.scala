package repositories.users.tables


import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.UserDemographics

import scala.concurrent.Future


sealed class UserDemographicsTable  extends CassandraTable[UserDemographicsTable,UserDemographics] {
  /** setting up or defining Person Demographics table attributes */

  object emailId               extends StringColumn(this) with PartitionKey
  object id                   extends StringColumn(this) with PrimaryKey
  object genderId             extends StringColumn(this)
  object raceId               extends StringColumn(this)
  object dateOfBirth          extends DateColumn(this)
  object maritalStatusId      extends StringColumn(this)
  object numberOfDependencies extends IntColumn(this)
  object date                 extends DateColumn(this)
  object state                extends StringColumn(this)
}

abstract class UserDemographicsTableImpl extends UserDemographicsTable with RootConnector{
  override lazy val tableName = "userDemographic"

  def save(pd: UserDemographics): Future[ResultSet] ={
    insert
      .value(_.id, pd.id)
      .value(_.emailId, pd.emailId)
      .value(_.genderId, pd.genderId)
      .value(_.raceId, pd.raceId)
      .value(_.dateOfBirth, pd.dateOfBirth)
      .value(_.maritalStatusId, pd.maritalStatusId)
      .value(_.numberOfDependencies, pd.numberOfDependencies)
      .value(_.date, pd.date)
      .value(_.state, pd.state)
      .future()
  }
  def findById(map: Map[String, String]): Future[Option[UserDemographics]] = {
    /** gets user demographics base on user id and db record id */
    select.where(_.emailId eqs map("emailId")).and(_.id eqs map("id")).one()
  }

  def findUserDemographics(emailId: String): Future[Seq[UserDemographics]] = {
    /** get all user demographics base on the user id */
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }
}
