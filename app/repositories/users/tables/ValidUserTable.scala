package repositories.users.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.ValidUser

import scala.concurrent.Future


abstract class ValidUserTable extends Table[ValidUserTable, ValidUser] {

  object siteId extends StringColumn with PartitionKey

  object userId extends StringColumn with PrimaryKey

  object date extends Col[LocalDateTime] with PrimaryKey

  object action extends StringColumn

}

abstract class ValidUserTableImpl extends ValidUserTable with RootConnector {

  override lazy val tableName = "validusers"

  def save(user: ValidUser): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.userId, user.userId)
      .value(_.date, user.date)
      .value(_.action, user.action)
      .future()
  }

  def isUserValid(siteId: String, userId: String): Future[Boolean] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.userId eqs userId)
      .one() map ((user: Option[ValidUser]) => user match {
      case Some(_) => true
      case None => false
    })
  }

  def getValidUserEvents(siteId: String,userId: String): Future[Seq[ValidUser]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.userId eqs userId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getValidUsers: Future[Int] = {
    select.fetchEnumerator() run Iteratee.collect() map ((users: Seq[ValidUser]) => users.toList.toSet[ValidUser].size)

  }
}


abstract class TimeLineValidUserTable extends Table[TimeLineValidUserTable, ValidUser] {

  object siteId extends StringColumn with PartitionKey

  object date extends Col[LocalDateTime] with PrimaryKey

  object userId extends StringColumn with PrimaryKey

  object action extends StringColumn

}

abstract class TimeLineValidUserTableImpl extends TimeLineValidUserTable with RootConnector {

  override lazy val tableName = "timelinevalidusers"

  def save(user: ValidUser): Future[ResultSet] = {
    insert
      .value(_.siteId, user.siteId)
      .value(_.userId, user.userId)
      .value(_.date, user.date)
      .value(_.action, user.action)
      .future()
  }

  def getValidUsersInLast24hours(siteId: String): Future[Seq[ValidUser]] = {
    val date = LocalDateTime.now()
    val last24hrs = date.minusHours(24.toLong)
    select
      .where(_.siteId eqs siteId)
      .and(_.date gt last24hrs)
      .fetchEnumerator() run Iteratee.collect()
  }
}
