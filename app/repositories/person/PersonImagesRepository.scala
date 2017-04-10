package repositories.person

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.person.tables.PersonImagesTableImpl


class PersonImagesDatabase (override val connector: KeySpaceDef) extends Database[PersonImagesDatabase](connector) {
  object personLanguageTable extends PersonImagesTableImpl with connector.Connector
}

object PersonImagesDatabase extends PersonImagesDatabase(DataConnection.connector)

trait PersonImagesRepository {
  def  database = PersonImagesDatabase
}
