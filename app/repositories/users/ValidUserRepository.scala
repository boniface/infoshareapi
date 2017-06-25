package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.{TimeLineValidUserTableImpl, ValidUserTableImpl}

class ValidUserDatabase(override val connector: KeySpaceDef) extends Database[ValidUserDatabase](connector) {

  object validUserTable extends ValidUserTableImpl with connector.Connector

  object timeLineValidUserTable extends TimeLineValidUserTableImpl with connector.Connector
}

object ValidUserDatabase extends ValidUserDatabase(DataConnection.connector)

trait ValidUserRepository {

  def database = ValidUserDatabase
}
