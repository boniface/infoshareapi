package repositories.person.tables


import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.PersonContact

import scala.concurrent.Future


class PersonContactTable extends CassandraTable[PersonContactTable,PersonContact] {
  /** setting up or defining person contacts table attributes */
  object id             extends StringColumn(this)  with PrimaryKey
  object personId       extends StringColumn(this)  with PartitionKey
  object addressTypeId  extends StringColumn(this)
  object contactNumber   extends StringColumn(this)
  object status         extends StringColumn(this)
  object date           extends DateColumn(this)
  object state          extends StringColumn(this)

}

abstract class PersonContactTableImpl extends PersonContactTable with RootConnector {
  override lazy val tableName = "personContacts"

  def save(pc: PersonContact): Future[ResultSet] = {
    /** save new person contact number details to the db */
    insert
      .value(_.id, pc.id)
      .value(_.personId, pc.personId)
      .value(_.addressTypeId, pc.addressTypeId)
      .value(_.contactNumber, pc.contactNumber)
      .value(_.status, pc.status)
      .value(_.date, pc.date)
      .value(_.state, pc.state)
      .future()
  }

  def findById(personId: String, id: String): Future[Option[PersonContact]] = {
    /** gets user phone number base on user id and db record id */
    select.where(_.personId eqs personId).and(_.id eqs id).one()
  }

  def findPersonContacts(personId: String): Future[Seq[PersonContact]] = {
    /** get all user contact numbers */
    select.where(_.personId eqs personId).fetchEnumerator() run Iteratee.collect()
  }
}