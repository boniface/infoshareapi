package repositories.users

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.users.tables.UserStatusTableImpl



/**
  * Created by hashcode on 2017/01/29.
  */
class UserStatusDatabase (override val connector: KeySpaceDef) extends Database[UserStatusDatabase](connector) {
  object userStatusTable extends UserStatusTableImpl with connector.Connector
}

object UserStatusDatabase extends UserStatusDatabase(DataConnection.connector)

trait UserStatusRepository  {
  def  database = UserStatusDatabase
}


