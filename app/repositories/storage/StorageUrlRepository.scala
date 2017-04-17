package repositories.storage

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.storage.tables.StorageUrlTableImpl


class StorageUrlDatabase (override val connector: KeySpaceDef) extends Database[StorageUrlDatabase](connector){
  object storageUrlTable  extends StorageUrlTableImpl with connector.Connector
}

object StorageUrlDatabase extends StorageUrlDatabase(DataConnection.connector)

trait StorageUrlRepository {
  def database = StorageUrlDatabase
}
