package repositories.content


import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.content.tables.PublishedContentTableImpl


class PublishedContentDatabase (override val connector: KeySpaceDef) extends Database[PublishedContentDatabase](connector){
  object publishedContentTable  extends PublishedContentTableImpl with connector.Connector
}

object publishedContentDatabase extends PublishedContentDatabase(DataConnection.connector)

trait PublishedContentRepository {
  def database = publishedContentDatabase
}


