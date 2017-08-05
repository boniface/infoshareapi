package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.CommentTableImpl



/**
  * Created by hashcode
  */
class CommentsDatabase (override val connector: KeySpaceDef) extends Database[CommentsDatabase](connector) {
  object commentsTable extends CommentTableImpl with connector.Connector
}

object CommentsDatabase extends CommentsDatabase(DataConnection.connector)

trait CommentsRepository  {
  def  database = CommentsDatabase
}

