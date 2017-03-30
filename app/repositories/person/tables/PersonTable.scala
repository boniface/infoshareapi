package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.Person

import scala.concurrent.Future


class PersonTable extends CassandraTable[PersonTable, Person]{

  object org  extends StringColumn(this) with  PartitionKey
  object id extends StringColumn(this) with PrimaryKey
  object emailAddress extends StringColumn(this) with PrimaryKey
  object firstName extends StringColumn(this)
  object lastName extends StringColumn(this)
  object password extends StringColumn(this)
  object enabled extends BooleanColumn(this)
  object accountNonExpired extends BooleanColumn(this)
  object credentialsNonExpired extends BooleanColumn(this)
  object accountNonLocked extends BooleanColumn(this)
  object state extends StringColumn(this)
}

abstract class  PersonTableImpl extends PersonTable with RootConnector {

  override lazy val tableName = "person"

  def save(person: Person): Future[ResultSet] = {
    insert
      .value(_.org, person.org)
      .value(_.id, person.id)
      .value(_.firstName, person.firstName)
      .value(_.emailAddress, person.emailAddress)
      .value(_.lastName, person.lastName)
      .value(_.password, person.password)
      .value(_.enabled, person.enabled)
      .value(_.accountNonExpired, person.accountNonExpired)
      .value(_.credentialsNonExpired, person.credentialsNonExpired)
      .value(_.accountNonLocked, person.accountNonLocked)
      .value(_.state, person.state)
      .future()
  }

  def getPerson(org: String, id:String): Future[Option[Person]] = {
    select.where(_.org eqs org) .and(_.id eqs id) .one()
  }

  def getPersonByEmail(emailAddress: String): Future[Option[Person]] = {
    select.where(_.emailAddress eqs emailAddress).one()
  }

  def getPeople(org: String): Future[Seq[Person]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }
}

