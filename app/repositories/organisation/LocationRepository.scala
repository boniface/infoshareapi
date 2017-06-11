package repositories.organisation

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.organisation.tables.LocationTableImpl


class LocationDatabase (override val connector: KeySpaceDef) extends Database[LocationDatabase](connector) {
  object locationTable extends LocationTableImpl with connector.Connector
}

object LocationDatabase extends LocationDatabase(DataConnection.connector)

trait LocationRepository {
  def  database = LocationDatabase
}

