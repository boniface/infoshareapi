package repositories.util

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.util.tables.KeysTableImpl


/**
  * Created by hashcode on 2017/01/29.
  */
class KeysDatabase(override val connector: KeySpaceDef) extends Database[KeysDatabase](connector) {

  object keysTable extends KeysTableImpl with connector.Connector

}

object KeysDatabase extends KeysDatabase(DataConnection.connector)

trait KeysRepository {
  def database = KeysDatabase
}


