package repositories.location

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.location.tables.LocationTypeTableImpl

class LocationTypeDatabase (override val connector: KeySpaceDef) extends Database[LocationTypeDatabase](connector) {
  object locationTypeTable extends LocationTypeTableImpl with connector.Connector
}

object LocationTypeDatabase extends LocationTypeDatabase(DataConnection.connector)

trait LocationTypeRepository {
  def  database = LocationTypeDatabase
}

