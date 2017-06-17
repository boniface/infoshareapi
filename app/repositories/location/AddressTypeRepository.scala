package repositories.location

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.location.tables.AddressTypeTableImpl

class AddressTypeDatabase(override val connector: KeySpaceDef) extends Database[AddressTypeDatabase](connector) {
  object addressTypeTable extends AddressTypeTableImpl with connector.Connector
}

object AddressTypeDatabase extends AddressTypeDatabase(DataConnection.connector)

trait AddressTypeRepository {
  def database = AddressTypeDatabase
}
