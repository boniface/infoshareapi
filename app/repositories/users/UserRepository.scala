package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.{PersonTableImpl, UserTableImpl}

class UserDatabase(override val connector: KeySpaceDef) extends Database[UserDatabase](connector) {

  object userTable extends UserTableImpl with connector.Connector

  object personTable extends PersonTableImpl with connector.Connector

}

object UserDatabase extends UserDatabase(DataConnection.connector)

trait UserRepository {
  def database = UserDatabase
}
