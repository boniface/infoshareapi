package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.UserTableImpl

/**
  * i think we making connection to database?
  * @param connector
  */

class UserDatabase (override val connector: KeySpaceDef) extends Database[UserDatabase](connector) {
  object UserTable extends UserTableImpl with connector.Connector
}

object UserDatabase extends UserDatabase(DataConnection.connector)

trait UserRepository {
  def  database = UserDatabase
}