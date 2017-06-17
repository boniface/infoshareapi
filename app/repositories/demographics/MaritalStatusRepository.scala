package repositories.demographics

import com.outworkers.phantom.dsl.{Database, KeySpaceDef}
import conf.connections.DataConnection
import repositories.demographics.tables.MaritalStatusTableImpl


class MaritalStatusDatabase(override val connector: KeySpaceDef) extends Database[MaritalStatusDatabase](connector) {
  object maritalStatusTable extends MaritalStatusTableImpl with connector.Connector
}

object MaritalStatusDatabase extends MaritalStatusDatabase(DataConnection.connector)

trait MaritalStatusRepository {
  def database = MaritalStatusDatabase
}