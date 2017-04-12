package repositories.location.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.location.AddressType

import scala.concurrent.Future

class AddressTypeTable extends CassandraTable[AddressTypeTable,AddressType]{
  object id extends StringColumn(this) with PartitionKey
  object name extends StringColumn(this)
  object state extends StringColumn(this)

}

abstract class AddressTypeTableImpl extends AddressTypeTable with RootConnector {
  override lazy val tableName = "addressType"

  def save(addtype: AddressType): Future[ResultSet] = {
    insert
      .value(_.id, addtype.id)
      .value(_.name, addtype.name)
      .value(_.state, addtype.state)
      .future()
  }

  def findById(id: String):Future[Option[AddressType]] = {
    select.where(_.id eqs id).one()
  }
  def findAll: Future[Seq[AddressType]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteById(id:String): Future[ResultSet] = {
    delete.where(_.id eqs id).future()
  }
}
