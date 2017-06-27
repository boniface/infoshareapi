package repositories.storage

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.storage.tables.FileResultsTableImpl

class FileResultsDatabase(override val connector: KeySpaceDef) extends Database[FileResultsDatabase](connector) {
  object fileResultsTable extends FileResultsTableImpl with connector.Connector
}

object FileResultsDatabase extends FileResultsDatabase(DataConnection.connector)

trait FileResultsRepository {
  def database = FileResultsDatabase
}
