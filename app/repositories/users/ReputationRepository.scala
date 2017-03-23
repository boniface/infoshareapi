package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.ReputationTableImpl



/**
  * Created by hashcode on 2017/01/29.
  */
class ReputationDatabase (override val connector: KeySpaceDef) extends Database[ReputationDatabase](connector) {
  object reputationTable extends ReputationTableImpl with connector.Connector
}

object ReputationDatabase extends ReputationDatabase(DataConnection.connector)

trait ReputationRepository  {
  def  database = ReputationDatabase
}




