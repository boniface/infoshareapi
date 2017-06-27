package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.UserDemographicsTableImpl


class UserDemographicsDatabase(override val connector: KeySpaceDef) extends Database[UserDemographicsDatabase](connector) {
  object userDemographicsTable extends UserDemographicsTableImpl with connector.Connector
}

object UserDemographicsDatabase extends UserDemographicsDatabase(DataConnection.connector)

trait UserDemographicsRepository {
    def database = UserDemographicsDatabase
}
