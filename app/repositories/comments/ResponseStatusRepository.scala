package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.ResponseStatusTableImpl


class ResponseStatusDatabase (override val connector: KeySpaceDef) extends Database[ResponseStatusDatabase](connector) {
  object responseStatusTable extends ResponseStatusTableImpl with connector.Connector
}

object ResponseStatusDatabase extends ResponseStatusDatabase(DataConnection.connector)

trait ResponseStatusRepository  {
  def  database = ResponseStatusDatabase
}


