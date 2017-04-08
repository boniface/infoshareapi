package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.PersonLanguageTableImpl


class PersonLanguageDatabase (override val connector: KeySpaceDef) extends Database[PersonLanguageDatabase](connector) {
  object personLanguageTable extends PersonLanguageTableImpl with connector.Connector
}

object PersonLanguageDatabase extends PersonLanguageDatabase(DataConnection.connector)

trait PersonLanguageRepository {
  def  database = PersonLanguageDatabase
}
