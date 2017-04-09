package repositories.person.tables

import com.outworkers.phantom.dsl._
import domain.person.Person

import scala.concurrent.Future


class UserTable extends CassandraTable[UserTable,Person]  {
  /** setting up or defining user table attributes */
  object emailAddress extends StringColumn(this) with PartitionKey
  object org extends StringColumn(this)
  object id extends StringColumn(this)
  object firstName extends StringColumn(this)
  object lastName extends StringColumn(this)
  object middleName extends StringColumn(this)
  object password extends StringColumn(this)
  object is_enabled extends BooleanColumn(this)
  object accountNonExpired extends BooleanColumn(this)
  object credentialsNonExpired extends BooleanColumn(this)
  object accountNonLocked extends BooleanColumn(this)
  object state extends StringColumn(this)
}


abstract class UserTableImpl extends UserTable with RootConnector {
  override lazy val tableName = "users"

  def save(person: Person): Future[ResultSet] = {
    /**save new user account to the db
      *where are you being used ?? */
    insert
      .value(_.org, person.org)
      .value(_.id, person.id)
      .value(_.firstName, person.firstName)
      .value(_.emailAddress, person.emailAddress)
      .value(_.lastName, person.lastName)
      .value(_.middleName, person.middleName)
      .value(_.password, person.password)
      .value(_.is_enabled, person.is_enabled)
      .value(_.accountNonExpired, person.accountNonExpired)
      .value(_.credentialsNonExpired, person.credentialsNonExpired)
      .value(_.accountNonLocked, person.accountNonLocked)
      .value(_.state, person.state)
      .future()
  }

  def findByEmail(email: String): Future[Option[Person]] = {
    /** get one user on that organization based on the email given  */
    select.where(_.emailAddress eqs email).one()
  }

}