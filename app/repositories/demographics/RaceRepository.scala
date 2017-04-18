package repositories.demographics

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.demographics.tables.RaceTableImpl

class RaceDatabase (override val connector: KeySpaceDef) extends Database[RaceDatabase](connector) {
  object raceTable extends RaceTableImpl with connector.Connector
}

object RaceDatabase extends RaceDatabase(DataConnection.connector)

trait RaceRepository {
  def  database = RaceDatabase
}

