package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.{AbuseByUserTableImpl, AbuseTableImpl}


class AbuseDatabase (override val connector: KeySpaceDef) extends Database[AbuseDatabase](connector) {

  object abuseTable extends AbuseTableImpl with connector.Connector

  object abuseByUserTable extends AbuseByUserTableImpl with connector.Connector
}

object AbuseDatabase extends AbuseDatabase(DataConnection.connector)

trait AbuseRepository  {
  def  database = AbuseDatabase
}


