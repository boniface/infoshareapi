package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.UserDemographicsTableImpl


class UserDemographicsDatabase(override val connector: KeySpaceDef) extends Database[UserDemographicsDatabase](connector) {
  object userLanguageTable extends UserDemographicsTableImpl with connector.Connector
}

object UserDemographicsDatabase extends UserDemographicsDatabase(DataConnection.connector)

trait UserDemographicsRepository {
    def database = UserDemographicsDatabase
}
