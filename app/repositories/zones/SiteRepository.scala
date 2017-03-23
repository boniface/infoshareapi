package repositories.zones

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.zones.tables.SiteTableImpl



/**
  * Created by hashcode on 2017/01/29.
  */
class SiteDatabase (override val connector: KeySpaceDef) extends Database[SiteDatabase](connector) {
  object siteTable extends SiteTableImpl with connector.Connector
}

object SiteDatabase extends SiteDatabase(DataConnection.connector)

trait SiteRepository  {
  def  database = SiteDatabase
}



