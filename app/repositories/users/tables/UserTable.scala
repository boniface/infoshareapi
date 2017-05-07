package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.User

import scala.concurrent.Future


sealed class UserTable extends CassandraTable[UserTable,User]  {
  /** setting up or defining user table attributes */
  object org extends StringColumn(this) with PartitionKey
  object email extends StringColumn(this) with PrimaryKey
  object firstName extends StringColumn(this)
  object lastName extends StringColumn(this)
  object middleName extends OptionalStringColumn(this)
  object screenName extends OptionalStringColumn(this)
  object password extends StringColumn(this)
  object state extends StringColumn(this)
  object date extends DateTimeColumn(this)
}


abstract class UserTableImpl extends UserTable with RootConnector {
  override lazy val tableName = "users"

  def save(user: User): Future[ResultSet] = {
    /**save new user account to the db where are you being used ?? */
    insert
      .value(_.org, user.org)
      .value(_.email, user.email)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.middleName, user.middleName)
      .value(_.screenName, user.screenName)
      .value(_.password, user.password)
      .value(_.state, user.state)
      .value(_.date, user.date)
      .future()
  }

  def getUsers(org: String): Future[Seq[User]] = {
    /** get one user on that organization based on the email given  */
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }
  def getUser(org:String, email: String): Future[Option[User]] = {
    /** get one user on that organization based on the email given  */
    select
      .where(_.org eqs org)
      .and(_.email eqs email).one()
  }

}

sealed class PersonTable extends CassandraTable[PersonTable, User]{
  /** setting up or defining person table attributes */
  object email extends StringColumn(this) with PartitionKey
  object org extends StringColumn(this) with PrimaryKey
  object firstName extends StringColumn(this)
  object lastName extends StringColumn(this)
  object middleName extends OptionalStringColumn(this)
  object screenName extends OptionalStringColumn(this)
  object password extends StringColumn(this)
  object state extends StringColumn(this)
  object date extends DateTimeColumn(this)
}

abstract class  PersonTableImpl extends PersonTable with RootConnector {

  override lazy val tableName = "persons"

  def save(user: User): Future[ResultSet] = {
    /** save new user account to the db */
    insert
      .value(_.org, user.org)
      .value(_.email, user.email)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.middleName, user.middleName)
      .value(_.screenName, user.screenName)
      .value(_.password, user.password)
      .value(_.state, user.state)
      .value(_.date, user.date)
      .future()
  }

    def getUserByEmail(email: String): Future[Seq[User]] = {
      /** get all users within that organization */
      select.where(_.email eqs email).fetchEnumerator() run Iteratee.collect()
    }

}

