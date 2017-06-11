package repositories.organisation

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.organisation.tables.OrganisationTableImpl


class OrganisationDatabase (override val connector: KeySpaceDef) extends Database[OrganisationDatabase](connector) {
  object organisationTable extends OrganisationTableImpl with connector.Connector
}

object OrganisationDatabase extends OrganisationDatabase(DataConnection.connector)

trait OrganisationRepository {
  def  database = OrganisationDatabase
}

