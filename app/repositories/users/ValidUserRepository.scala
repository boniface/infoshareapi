package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.ValidUserTableImpl

/**
  * Created by hashcode on 2017/06/11.
  */
class ValidUserDatabase(override val connector: KeySpaceDef) extends Database[ValidUserDatabase](connector) {

  object validUserTable extends ValidUserTableImpl with connector.Connector

}

object ValidUserDatabase extends ValidUserDatabase(DataConnection.connector)

trait ValidUserRepository {

  def database = ValidUserDatabase
}
