package repositories.demographics

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.demographics.tables.GenderTableImpl

class GenderDatabase (override val connector: KeySpaceDef) extends Database[GenderDatabase](connector) {
  object genderTable extends GenderTableImpl with connector.Connector
}

object GenderDatabase extends GenderDatabase(DataConnection.connector)

trait GenderRepository {
  def  database = GenderDatabase
}

