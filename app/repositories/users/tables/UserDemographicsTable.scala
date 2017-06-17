package repositories.users.tables

import java.time.{LocalDateTime =>Date}
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.UserDemographics

import scala.concurrent.Future


abstract class UserDemographicsTable  extends Table[UserDemographicsTable,UserDemographics] {

  object emailId extends StringColumn with PartitionKey
  
  object id extends StringColumn with PrimaryKey
  
  object genderId extends StringColumn
  
  object raceId extends StringColumn
  
  object dateOfBirth extends Col[Date]
  
  object maritalStatusId extends StringColumn
  
  object numberOfDependencies extends IntColumn
  
  object date extends Col[Date]
  
  object state extends StringColumn
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
