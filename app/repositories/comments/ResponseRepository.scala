package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.ResponseTableImpl



/**
  * Created by hashcode
  */
class ResponseDatabase (override val connector: KeySpaceDef) extends Database[ResponseDatabase](connector) {
  object responseTable extends ResponseTableImpl with connector.Connector
}

object ResponseDatabase extends ResponseDatabase(DataConnection.connector)

trait ResponseRepository  {
  def  database = ResponseDatabase
}


