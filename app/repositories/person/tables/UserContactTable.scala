package repositories.person.tables


import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.UserContact

import scala.concurrent.Future


class UserContactTable extends CassandraTable[UserContactTable,UserContact] {
  /** setting up or defining person contacts table attributes */
  object id             extends StringColumn(this)  with PrimaryKey
  object userId       extends StringColumn(this)  with PartitionKey
  object addressTypeId  extends StringColumn(this)
  object contactNumber   extends StringColumn(this)
  object status         extends StringColumn(this)
  object date           extends DateColumn(this)
  object state          extends StringColumn(this)

}

abstract class UserContactTableImpl extends UserContactTable with RootConnector {
  override lazy val tableName = "personContacts"

  def save(pc: UserContact): Future[ResultSet] = {
    /** save new person contact number details to the db */
    insert
      .value(_.id, pc.id)
      .value(_.userId, pc.userId)
      .value(_.addressTypeId, pc.addressTypeId)
      .value(_.contactNumber, pc.contactNumber)
      .value(_.status, pc.status)
      .value(_.date, pc.date)
      .value(_.state, pc.state)
      .future()
  }

  def findById(userId: String, id: String): Future[Option[UserContact]] = {
    /** gets user phone number base on user id and db record id */
    select.where(_.userId eqs userId).and(_.id eqs id).one()
  }

  def findPersonContacts(userId: String): Future[Seq[UserContact]] = {
    /** get all user contact numbers */
    select.where(_.userId eqs userId).fetchEnumerator() run Iteratee.collect()
  }
}
