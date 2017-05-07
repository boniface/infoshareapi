package repositories.demographics

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.demographics.tables.RoleTableImpl

class RoleDatabase(override val connector: KeySpaceDef) extends Database[RoleDatabase](connector) {
  object roleTable extends RoleTableImpl with connector.Connector
}

object RoleDatabase extends RoleDatabase(DataConnection.connector)

trait RoleRepository {
  def  database = RoleDatabase
}

