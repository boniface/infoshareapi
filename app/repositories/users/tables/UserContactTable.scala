package repositories.users.tables


import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.UserContact

import scala.concurrent.Future


abstract class UserContactTable extends Table[UserContactTable, UserContact] {

  object emailId extends StringColumn  with PartitionKey

  object id extends StringColumn  with PrimaryKey

  object contactTypeId  extends StringColumn

  object contactNumber  extends StringColumn

  object date extends Col[LocalDateTime]

  object state extends StringColumn

}

abstract class UserContactTableImpl extends UserContactTable with RootConnector {
  override lazy val tableName = "userContact"

  def save(obj: UserContact): Future[ResultSet] = {
    insert
      .value(_.emailId, obj.emailId)
      .value(_.id, obj.id)
      .value(_.contactTypeId, obj.contactTypeId)
      .value(_.contactNumber, obj.contactNumber)
      .value(_.date, obj.date)
      .value(_.state, obj.state)
      .future()
  }

  def findById(map: Map[String, String]): Future[Option[UserContact]] = {
    select.where(_.emailId eqs map("emailId")).and(_.id eqs map("id")).one()
  }

  def findUserContacts(emailId: String): Future[Seq[UserContact]] = {
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }
}
