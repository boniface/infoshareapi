package repositories.zones

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.zones.tables.AdministratorsTableImpl


/**
  * Created by hashcode on 2017/01/29.
  */
class AdministratorsDatabase(override val connector: KeySpaceDef) extends Database[AdministratorsDatabase](connector) {

  object administratorTable extends AdministratorsTableImpl with connector.Connector

}

object AdministratorsDatabase extends AdministratorsDatabase(DataConnection.connector)

trait AdministratorRepository {
  def database = AdministratorsDatabase
}



