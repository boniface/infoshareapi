package repositories.util

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.util.tables.RoleTableImpl


class RolesDatabase(override val connector: KeySpaceDef) extends Database[RolesDatabase](connector) {
  object rolesTable extends RoleTableImpl with connector.Connector
}

object RolesDatabase extends RolesDatabase(DataConnection.connector)

trait RolesRepository {
  def database = RolesDatabase
}


