package repositories.person.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.person.PersonAddress

import scala.concurrent.Future



class PersonAddressTable extends CassandraTable[PersonAddressTable, PersonAddress] {

  object personId extends StringColumn(this) with PartitionKey
  object id extends StringColumn(this) with PrimaryKey
  object addressTypeId extends StringColumn(this)
  object description extends StringColumn(this)
  object postalCode extends StringColumn(this)
  object date extends DateColumn(this)
  object state extends StringColumn(this)

}
abstract class PersonAddressTableImpl extends PersonAddressTable with RootConnector{

  def save(personAddress: PersonAddress): Future[ResultSet] = {
    insert
      .value(_.personId, personAddress.personId)
      .value(_.id, personAddress.id)
      .value(_.description, personAddress.description)
      .value(_.postalCode, personAddress.postalCode)
      .value(_.addressTypeId, personAddress.addressTypeId)
      .value(_.date, personAddress.date)
      .value(_.state, personAddress.state)
      .future()
  }

  def findById(personId: String, id: String): Future[Option[PersonAddress]] = {
    select.where(_.personId eqs personId).and(_.id eqs id).one()
  }

  def findAllAddress(personId: String): Future[Seq[PersonAddress]] = {
    select.where(_.personId eqs personId).fetchEnumerator() run Iteratee.collect()
  }
}