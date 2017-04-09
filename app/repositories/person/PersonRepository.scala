package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.PersonTableImpl

/**
  * i think we making connection to database?
  * @param connector
  */

class PersonDatabase (override val connector: KeySpaceDef) extends Database[PersonDatabase](connector) {
  object personTable extends PersonTableImpl with connector.Connector
}

object PersonDatabase extends PersonDatabase(DataConnection.connector)

trait PersonRepository {
  def  database = PersonDatabase
}
