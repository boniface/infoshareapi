package repositories.util

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.util.tables.TokenTableImpl



/**
  * Created by hashcode on 2017/01/29.
  */
class TokenDatabase (override val connector: KeySpaceDef) extends Database[TokenDatabase](connector) {
  object tokenTable extends TokenTableImpl with connector.Connector
}

object TokenDatabase extends TokenDatabase(DataConnection.connector)

trait TokenRepository  {

  def  database = TokenDatabase
}




