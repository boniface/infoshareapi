package repositories.users.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.User

import scala.concurrent.Future


abstract class UserSiteTable extends Table[UserSiteTable, User] {

  object siteId extends StringColumn with PartitionKey

  object email extends StringColumn with PrimaryKey

  object firstName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object middleName extends OptionalStringColumn

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
      .value(_.middleName, user.middleName)
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
