package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.PersonContactTableImpl

class PersonContactDatabase (override val connector: KeySpaceDef) extends Database[PersonContactDatabase](connector) {
  object personContactTable extends PersonContactTableImpl with connector.Connector
}

object PersonContactDatabase extends PersonContactDatabase(DataConnection.connector)

trait PersonContactRepository {
  def  database = PersonContactDatabase
}