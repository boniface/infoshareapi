package repositories.location.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.location.AddressType

import scala.concurrent.Future

abstract class AddressTypeTable extends Table[AddressTypeTable,AddressType]{
  
  object id extends StringColumn with PartitionKey
  
  object name extends StringColumn
  
  object state extends StringColumn

}

abstract class AddressTypeTableImpl extends AddressTypeTable with RootConnector {
  override lazy val tableName = "addrType"

  def save(addr: AddressType): Future[ResultSet] = {
    insert
      .value(_.id, addr.id)
      .value(_.name, addr.name)
      .value(_.state, addr.state)
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
