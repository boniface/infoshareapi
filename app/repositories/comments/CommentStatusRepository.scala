package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.CommentStatusTableImpl


class CommentStatusDatabase (override val connector: KeySpaceDef) extends Database[CommentStatusDatabase](connector) {
  object commentStatusTable extends CommentStatusTableImpl with connector.Connector
}

object CommentStatusDatabase extends CommentStatusDatabase(DataConnection.connector)

trait CommentStatusRepository  {
  def  database = CommentStatusDatabase
}

