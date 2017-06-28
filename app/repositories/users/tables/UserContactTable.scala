package repositories.users.tables


import java.time.LocalDateTime
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.UserContact

import scala.concurrent.Future


abstract class UserContactTable extends Table[UserContactTable,UserContact] {
  object emailId extends StringColumn  with PartitionKey

  object id extends StringColumn  with PrimaryKey

  object addressTypeId  extends StringColumn

  object contactNumber  extends StringColumn

  object date extends Col[LocalDateTime]

  object state extends StringColumn

}

abstract class UserContactTableImpl extends UserContactTable with RootConnector {
  override lazy val tableName = "userContactz"

  def save(pc: UserContact): Future[ResultSet] = {
    insert
      .value(_.emailId, pc.emailId)
      .value(_.id, pc.id)
      .value(_.addressTypeId, pc.addressTypeId)
      .value(_.contactNumber, pc.contactNumber)
      .value(_.date, pc.date)
      .value(_.state, pc.state)
      .future()
  }

  def findById(map: Map[String, String]): Future[Option[UserContact]] = {
    /** gets user phone number base on user id and db record id */
    select.where(_.emailId eqs map("emailId")).and(_.id eqs map("id")).one()
  }

  def findUserContacts(emailId: String): Future[Seq[UserContact]] = {
    /** get all user contact numbers */
    select.where(_.emailId eqs emailId).fetchEnumerator() run Iteratee.collect()
  }
}
