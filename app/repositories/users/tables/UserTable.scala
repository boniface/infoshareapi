package repositories.users.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.users.User

import scala.concurrent.Future

/**
  * Created siteId:String,
  * email: String,
  * screenName: String,
  * firstname:Option[String],
  * lastName:Option[String],
  * password: String
  */

class UserTable extends CassandraTable[UserTable, User] {

  object email extends StringColumn(this) with PartitionKey

  object siteId extends StringColumn(this)

  object state extends StringColumn(this)

  object screenName extends StringColumn(this)

  object firstname extends OptionalStringColumn(this)

  object lastName extends OptionalStringColumn(this)

  object password extends StringColumn(this)

  object date extends DateTimeColumn(this)

}

abstract class UserTableImpl extends UserTable with RootConnector {

  override lazy val tableName = "users"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.email, user.email)
      .value(_.state, user.state)
      .value(_.screenName, user.screenName)
      .value(_.firstname, user.firstname)
      .value(_.lastName, user.lastName)
      .value(_.password, user.password)
      .value(_.date, user.date)
      .future()
  }

  def getUser(email: String): Future[Option[User]] = {
    select.where(_.email eqs email).one()
  }

  def getUsers: Future[Seq[User]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteUser(email: String): Future[ResultSet] = {
    delete.where(_.email eqs email).future()
  }
}

sealed class UserSiteTable extends CassandraTable[UserSiteTable, User] {

  object siteId extends StringColumn(this) with PartitionKey

  object email extends StringColumn(this) with PrimaryKey

  object state extends StringColumn(this)

  object screenName extends StringColumn(this)

  object firstname extends OptionalStringColumn(this)

  object lastName extends OptionalStringColumn(this)

  object password extends StringColumn(this)

  object date extends DateTimeColumn(this)

}

abstract class UserSiteTableImpl extends UserSiteTable with RootConnector {

  override lazy val tableName = "siteusers"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.email, user.email)
      .value(_.state, user.state)
      .value(_.screenName, user.screenName)
      .value(_.firstname, user.firstname)
      .value(_.lastName, user.lastName)
      .value(_.password, user.password)
      .value(_.date, user.date)
      .future()
  }

  def getSiteUsers(siteId: String): Future[Seq[User]] = {
    select.where(_.siteId eqs siteId).fetchEnumerator() run Iteratee.collect()
  }

  def deleteUser(siteId: String, email: String): Future[ResultSet] = {
    delete.where(_.siteId eqs siteId).and(_.email eqs email).future()
  }
}

