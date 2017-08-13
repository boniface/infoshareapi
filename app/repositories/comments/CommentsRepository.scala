package repositories.comments

import com.outworkers.phantom.dsl._
import conf.connections.DataConnection
import repositories.comments.tables.{CommentTableImpl, CommentsByUserTableImpl, SingleCommentTableImpl}


class CommentsDatabase (override val connector: KeySpaceDef) extends Database[CommentsDatabase](connector) {

  object commentsTable extends CommentTableImpl with connector.Connector

  object commentsByUserTable extends CommentsByUserTableImpl with connector.Connector

  object singleCommentsTable extends SingleCommentTableImpl with connector.Connector
}

object CommentsDatabase extends CommentsDatabase(DataConnection.connector)

trait CommentsRepository  {
  def  database = CommentsDatabase
}

