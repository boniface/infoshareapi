package repositories.util

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.util.tables.ItemStatusTableImpl


/**
  * Created by hashcode on 2017/01/29.
  */
class ItemStatusDatabase(override val connector: KeySpaceDef) extends Database[ItemStatusDatabase](connector) {

  object itemStatusTable extends ItemStatusTableImpl with connector.Connector

}

object ItemStatusDatabase extends ItemStatusDatabase(DataConnection.connector)

trait ItemStatusRepository {
  def database = ItemStatusDatabase
}


