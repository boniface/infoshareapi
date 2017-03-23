package repositories.zones

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.zones.tables.ZoneTableImpl



/**
  * Created by hashcode on 2017/01/29.
  */
class ZoneDatabase (override val connector: KeySpaceDef) extends Database[ZoneDatabase](connector) {
  object zoneTable extends ZoneTableImpl with connector.Connector
}

object ZoneDatabase extends ZoneDatabase(DataConnection.connector)

trait ZoneRepository  {
  def  database = ZoneDatabase
}



//// TEST REPO
//object TestRepo extends ZoneRepository(DataConnection.testConnector)
//
//trait TestRepositoryProvider {
//  def repository: ZoneRepository
//}
//
//trait TestRepository extends TestRepositoryProvider {
//  override val repository = TestRepo
//}


