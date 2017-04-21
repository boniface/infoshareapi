package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.UserAddressTableImpl


class UserAddressDatabase(override val connector: KeySpaceDef) extends Database[UserAddressDatabase](connector){
  object userAddressTable  extends UserAddressTableImpl with connector.Connector
}

object UserAddressDatabase extends UserAddressDatabase(DataConnection.connector)

trait UserAddressRepository {
  def database = UserAddressDatabase
}
