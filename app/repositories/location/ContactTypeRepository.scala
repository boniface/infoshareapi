package repositories.location

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.location.tables.ContactTypeTableImpl

class ContactTypeDatabase(override val connector: KeySpaceDef) extends Database[ContactTypeDatabase](connector) {
  object contactTypeTable extends ContactTypeTableImpl with connector.Connector
}

object ContactTypeDatabase extends ContactTypeDatabase(DataConnection.connector)

trait ContactTypeRepository {
  def database = ContactTypeDatabase
}
