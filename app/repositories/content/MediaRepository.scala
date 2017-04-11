package repositories.content


import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.content.tables.MediaTableImpl


class MediaDatabase (override val connector: KeySpaceDef) extends Database[MediaDatabase](connector){
  object mediaTable  extends MediaTableImpl with connector.Connector
}

object mediaDatabase extends MediaDatabase(DataConnection.connector)

trait MediaRepository {
  def database = mediaDatabase
}


