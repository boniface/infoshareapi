package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.PersonDemographicsTableImpl


class PersonDemographicsDatabase (override val connector: KeySpaceDef) extends Database[PersonDemographicsDatabase](connector) {
  object personLanguageTable extends PersonDemographicsTableImpl with connector.Connector
}

object PersonDemographicsDatabase extends PersonDemographicsDatabase(DataConnection.connector)

trait PersonDemographicsRepository {
    def database = PersonDemographicsDatabase
}
