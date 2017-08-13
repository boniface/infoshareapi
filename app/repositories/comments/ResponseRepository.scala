package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.{ResponseByUserTableImpl, ResponseTableImpl, SingleResponseTableImpl}


class ResponseDatabase (override val connector: KeySpaceDef) extends Database[ResponseDatabase](connector) {

  object responseTable extends ResponseTableImpl with connector.Connector

  object responseByUserTable extends ResponseByUserTableImpl with connector.Connector

  object singleResponseTable extends SingleResponseTableImpl with connector.Connector
}

object ResponseDatabase extends ResponseDatabase(DataConnection.connector)

trait ResponseRepository  {
  def  database = ResponseDatabase
}


