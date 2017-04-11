package repositories.util

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.util.tables.RoleTableImpl


/**
  * Created by hashcode on 2017/01/29.
  */
class RoleDatabase(override val connector: KeySpaceDef) extends Database[RoleDatabase](connector) {

  object roleTable extends RoleTableImpl with connector.Connector

}

object RoleDatabase extends RoleDatabase(DataConnection.connector)

trait RoleRepository {
  def database = RoleDatabase
}


