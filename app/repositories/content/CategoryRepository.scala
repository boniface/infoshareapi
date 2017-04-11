package repositories.content


import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.content.tables.CategoryTableImpl


class CategoryDatabase (override val connector: KeySpaceDef) extends Database[CategoryDatabase](connector){
  object categoryTable  extends CategoryTableImpl with connector.Connector
}

object categoryDatabase extends CategoryDatabase(DataConnection.connector)

trait CategoryRepository {
  def database = categoryDatabase
}


