package repositories.content


import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.content.tables.EditedContentTableImpl


class EditedContentDatabase (override val connector: KeySpaceDef) extends Database[EditedContentDatabase](connector){
  object editedContentTable  extends EditedContentTableImpl with connector.Connector
}

object EditedContentDatabase extends EditedContentDatabase(DataConnection.connector)

trait EditedContentRepository {
  def database = EditedContentDatabase
}

