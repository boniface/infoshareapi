package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.UserImagesTableImpl


class UserImagesDatabase(override val connector: KeySpaceDef) extends Database[UserImagesDatabase](connector) {
  object userLanguageTable extends UserImagesTableImpl with connector.Connector
}

object UserImagesDatabase extends UserImagesDatabase(DataConnection.connector)

trait UserImagesRepository {
  def  database = UserImagesDatabase
}
