package repositories.users.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.User

import scala.concurrent.Future


abstract class UserTable extends Table[UserTable, User] {

  object siteId extends StringColumn with PartitionKey

  object email extends StringColumn with PrimaryKey

  object firstName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object screenName extends StringColumn

  object password extends StringColumn

  object state extends StringColumn

  object date extends Col[LocalDateTime]

}

abstract class UserTableImpl extends UserTable with RootConnector {

  override lazy val tableName = "users"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.email, user.email)
      .value(_.state, user.state)
      .value(_.screenName, user.screenName)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.password, user.password)
      .value(_.date, user.date)
      .future()
  }

  def getUser(email: String, siteId:String): Future[Option[User]] = {
    select.where(_.email eqs email).and(_.siteId eqs siteId).one()
  }

  def getUserAccounts(email: String):Future[Seq[User]] = {
    select.where(_.email eqs email).fetchEnumerator() run Iteratee.collect()
  }

  def getUsers: Future[Seq[User]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteUser(email: String, siteId:String): Future[ResultSet] = {
    delete.where(_.email eqs email).and(_.siteId eqs siteId).future()
  }
}

abstract class UserSiteTable extends Table[UserSiteTable, User] {

  object siteId extends StringColumn with PartitionKey

  object email extends StringColumn with PrimaryKey

  object firstName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object screenName extends StringColumn

  object password extends StringColumn

  object state extends StringColumn

  object date extends Col[LocalDateTime]

}

abstract class UserSiteTableImpl extends UserSiteTable with RootConnector {

  override lazy val tableName = "siteusers"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.email, user.email)
      .value(_.state, user.state)
      .value(_.screenName, user.screenName)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.password, user.password)
      .value(_.date, user.date)
      .future()
  }

  def getSiteUsers(siteId: String): Future[Seq[User]] = {
    select.where(_.siteId eqs siteId).fetchEnumerator() run Iteratee.collect()
  }

  def getSiteUser(siteId: String, email: String): Future[Option[User]] = {
    select.where(_.siteId eqs siteId).and(_.email eqs email).one
  }

  def deleteUser(siteId: String, email: String): Future[ResultSet] = {
    delete.where(_.siteId eqs siteId).and(_.email eqs email).future()
  }
}


abstract class UserTimeLineTable extends Table[UserTimeLineTable, User] {

  object siteId extends StringColumn with PartitionKey

  object email extends StringColumn with PrimaryKey

  object firstName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object screenName extends StringColumn

  object password extends StringColumn

  object state extends StringColumn

  object date extends Col[LocalDateTime]  with PrimaryKey

}


abstract class UserTimeLineTableImpl extends UserTimeLineTable with RootConnector {

  override lazy val tableName = "timelineusers"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.email, user.email)
      .value(_.state, user.state)
      .value(_.screenName, user.screenName)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.password, user.password)
      .value(_.date, user.date)
      .future()
  }

  def getUsersAccountsOlderThanOneDay(siteId:String): Future[Seq[User]] = {
    val date = LocalDateTime.now()
    val last24hrs = date.minusHours(24.toLong)
    val last48hrs = date.minusHours(48.toLong)
    select.where(_.siteId lt siteId).and(_.date lt last24hrs)
      .and(_.date gt last48hrs).fetchEnumerator() run Iteratee.collect()
  }

  def getUsersCreateAfterPeriod(siteId:String, date: LocalDateTime): Future[Seq[User]] = {
    select.where(_.siteId eqs siteId).and(_.date gt date)
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteUser(date: LocalDateTime,siteId: String, email: String): Future[ResultSet] = {
    delete.where(_.siteId eqs siteId).and(_.date eqs date)
      .and(_.email eqs email).future()
  }
}