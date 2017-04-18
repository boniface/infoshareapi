package repositories.organisation

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.organisation.tables.OrganisationLogoTableImpl

class OrganisationLogoDatabase (override val connector: KeySpaceDef) extends Database[OrganisationLogoDatabase](connector) {
  object organisationLogoTable extends OrganisationLogoTableImpl with connector.Connector
}

object OrganisationLogoDatabase extends OrganisationLogoDatabase(DataConnection.connector)

trait OrganisationLogoRepository {
  def  database = OrganisationLogoDatabase
}

