package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection

class PersonDatabase (override val connector: KeySpaceDef) extends Database[PersonDatabase](connector) {
  object reputationTable extends ReputationTableImpl with connector.Connector
}

object PersonDatabase extends PersonDatabase(DataConnection.connector)
trait PersonRepository {
  def  database =
}
