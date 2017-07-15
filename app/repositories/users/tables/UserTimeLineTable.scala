package repositories.users.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.User

import scala.concurrent.Future


abstract class UserTimeLineTable extends Table[UserTimeLineTable, User] {

  object siteId extends StringColumn with PrimaryKey

  object email extends StringColumn with PrimaryKey

  object firstName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object middleName extends OptionalStringColumn

  object screenName extends StringColumn

  object password extends StringColumn

  object state extends StringColumn

  object date extends Col[LocalDateTime] with PartitionKey


}

abstract class UserTimeLineTableImpl extends UserTimeLineTable with RootConnector {

  override lazy val tableName = "timelineusers"

  def save(user: User): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.email, user.email)
      .value(_.state, user.state)
      .value(_.middleName, user.middleName)
      .value(_.screenName, user.screenName)
      .value(_.firstName, user.firstName)
      .value(_.lastName, user.lastName)
      .value(_.password, user.password)
      .value(_.date, user.date)
      .future()
  }

  def getUsersAccountsOlderThanOneDay: Future[Seq[User]] = {
    val date = LocalDateTime.now()
    val last24hrs = date.minusHours(24.toLong)
    val last48hrs = date.minusHours(48.toLong)
    select.where(_.date lt last24hrs).and(_.date gt last48hrs)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getUsersCreateAfterPeriod(date: LocalDateTime): Future[Seq[User]] = {
    select.where(_.date gt date).fetchEnumerator() run Iteratee.collect()
  }

  def deleteUser(date: LocalDateTime,siteId: String, email: String): Future[ResultSet] = {
    delete.where(_.date eqs date).and(_.siteId eqs siteId).and(_.email eqs email)
      .future()
  }
}


