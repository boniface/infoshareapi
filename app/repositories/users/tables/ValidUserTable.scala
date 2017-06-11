package repositories.users.tables

import java.time.{LocalDateTime =>Date}

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.ValidUser

import scala.concurrent.Future


abstract class ValidUserTable extends Table[ValidUserTable, ValidUser] {

  object userId extends StringColumn with PartitionKey

  object date extends Col[Date] with PrimaryKey

  object action extends StringColumn

}

abstract class ValidUserTableImpl extends ValidUserTable with RootConnector {

  override lazy val tableName = "validusers"

  def save(user: ValidUser): Future[ResultSet] = {
    insert
      .value(_.userId, user.userId)
      .value(_.date, user.date)
      .value(_.action, user.action)
      .future()
  }

  def isUserValid(userId: String): Future[Boolean] = {
    select.where(_.userId eqs userId).one() map ((user: Option[ValidUser]) => user match {
      case Some(_) => true
      case None => false
    })
  }

  def getValidUserEvents(userId: String): Future[Seq[ValidUser]] = {
    select.where(_.userId eqs userId).fetchEnumerator() run Iteratee.collect()
  }

  def getValidUsers: Future[Int] = {
    select.fetchEnumerator() run Iteratee.collect() map ((users: Seq[ValidUser]) => users.toList.toSet[ValidUser].size)

  }
}

