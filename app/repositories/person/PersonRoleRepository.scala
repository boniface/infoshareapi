package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.PersonRoleTableImpl


class PersonRoleDatabase (override val connector: KeySpaceDef) extends Database[PersonRoleDatabase](connector) {
  object personRoleTable extends PersonRoleTableImpl with connector.Connector
}

object personRoleDatabase extends PersonRoleDatabase(DataConnection.connector)

trait PersonRoleRepository {
  def  database = personRoleDatabase
}

