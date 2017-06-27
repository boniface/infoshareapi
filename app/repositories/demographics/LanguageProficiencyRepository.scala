package repositories.demographics

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.demographics.tables.LanguageProficiencyTableImpl

class LanguageProficiencyDatabase(override val connector: KeySpaceDef) extends Database[LanguageProficiencyDatabase](connector) {
  object languageProficiencyTable extends LanguageProficiencyTableImpl with connector.Connector
}

object LanguageProficiencyDatabase extends LanguageProficiencyDatabase(DataConnection.connector)

trait LanguageProficiencyRepository {
  def database = LanguageProficiencyDatabase
}
