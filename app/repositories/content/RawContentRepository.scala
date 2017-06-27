package repositories.content

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.content.tables.RawContentTableImpl

class RawContentDatabase(override val connector: KeySpaceDef) extends Database[RawContentDatabase](connector) {
  object rawContentTable extends RawContentTableImpl with connector.Connector
}

object RawContentDatabase extends RawContentDatabase(DataConnection.connector)

trait RawContentRepository {
  def database = RawContentDatabase
}
