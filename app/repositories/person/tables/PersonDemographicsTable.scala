package repositories.person.tables


import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.PersonDemographics

import scala.concurrent.Future


class PersonDemographicsTable  extends CassandraTable[PersonDemographicsTable,PersonDemographics] {
  /** setting up or defining Person Demographics table attributes */

  object id                   extends StringColumn(this) with PrimaryKey
  object personId             extends StringColumn(this) with PartitionKey
  object genderId             extends StringColumn(this)
  object raceId               extends StringColumn(this)
  object dateOfBirth          extends DateColumn(this)
  object maritalStatusId      extends StringColumn(this)
  object numberOfDependencies extends StringColumn(this)
  object date                 extends DateColumn(this)
}

abstract class PersonDemographicsTableImpl extends PersonDemographicsTable with RootConnector{
  override lazy val tableName = "personDemo"

  def save(pd: PersonDemographics): Future[ResultSet] ={
    /**save new Person Demographics record to the db */

    insert
        .value(_.id,pd.id)
        .value(_.personId,pd.personId)
        .value(_.genderId,pd.genderId)
        .value(_.raceId,pd.raceId)
        .value(_.dateOfBirth,pd.dateOfBirth)
        .value(_.maritalStatusId,pd.maritalStatusId)
        .value(_.numberOfDependencies,pd.numberOfDependencies)
        .value(_.date,pd.date)
      .future()
  }
  def findById(personId: String, id: String): Future[Option[PersonDemographics]] = {
    /** gets user demographics base on user id and db record id */
    select.where(_.personId eqs personId).and(_.id eqs id).one()
  }

  def findPersonDemographics(personId: String): Future[Seq[PersonDemographics]] = {
    /** get all user demographics base on the user id */
    select.where(_.personId eqs personId).fetchEnumerator() run Iteratee.collect()
  }
}
