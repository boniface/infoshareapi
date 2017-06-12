package repositories.content

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.content.tables.ContentTypeTableImpl

class ContentTypeDatabase(override val connector: KeySpaceDef) extends Database[ContentTypeDatabase](connector) {
  object contentTypeTable extends ContentTypeTableImpl with connector.Connector
}

object ContentTypeDatabase extends ContentTypeDatabase(DataConnection.connector)

trait ContentTypeRepository {
  def database = ContentTypeDatabase
}
