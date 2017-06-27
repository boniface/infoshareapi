package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.UserImagesTableImpl

class UserImagesDatabase(override val connector: KeySpaceDef) extends Database[UserImagesDatabase](connector) {
  object userImagesTable extends UserImagesTableImpl with connector.Connector
}

object UserImagesDatabase extends UserImagesDatabase(DataConnection.connector)

trait UserImagesRepository {
  def database = UserImagesDatabase
}
