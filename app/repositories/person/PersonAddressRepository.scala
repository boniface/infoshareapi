package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.PersonAddressTableImpl


class PersonAddressDatabase (override val connector: KeySpaceDef) extends Database[PersonAddressDatabase](connector){
  object personAddressTable  extends PersonAddressTableImpl with connector.Connector
}

object personAddressDatabase extends PersonAddressDatabase(DataConnection.connector)

trait PersonAddressRepository {
  def database = personAddressDatabase
}
