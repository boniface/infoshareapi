package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.UserLanguageTableImpl


class UserLanguageDatabase(override val connector: KeySpaceDef) extends Database[UserLanguageDatabase](connector) {
  object userLanguageTable extends UserLanguageTableImpl with connector.Connector
}

object UserLanguageDatabase extends UserLanguageDatabase(DataConnection.connector)

trait UserLanguageRepository {
  def database = UserLanguageDatabase
}
