package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.UserContactTableImpl

class UserContactDatabase(override val connector: KeySpaceDef) extends Database[UserContactDatabase](connector) {
  object userContactTable extends UserContactTableImpl with connector.Connector
}

object UserContactDatabase extends UserContactDatabase(DataConnection.connector)

trait UserContactRepository {
  def  database = UserContactDatabase
}
