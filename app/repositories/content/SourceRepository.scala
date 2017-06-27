package repositories.content

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.content.tables.SourceTableImpl

class SourceDatabase(override val connector: KeySpaceDef) extends Database[SourceDatabase](connector) {
  object sourceTable extends SourceTableImpl with connector.Connector
}

object SourceDatabase extends SourceDatabase(DataConnection.connector)

trait SourceRepository {
  def database = SourceDatabase
}
