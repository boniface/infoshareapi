package repositories.demographics

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.demographics.tables.LanguageTableImpl

class LanguageDatabase(override val connector: KeySpaceDef) extends Database[LanguageDatabase](connector) {
  object languageTable extends LanguageTableImpl with connector.Connector
}

object LanguageDatabase extends LanguageDatabase(DataConnection.connector)

trait LanguageRepository {
  def database = LanguageDatabase
}
