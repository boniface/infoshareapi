package repositories.comments.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.comments.CommentStatus

import scala.concurrent.Future



abstract class CommentStatusTable extends Table[CommentStatusTable, CommentStatus] {

  object commentId extends StringColumn with PartitionKey

  object date extends Col[LocalDateTime]  with PrimaryKey with ClusteringOrder with Ascending

  object status extends StringColumn


}

abstract class CommentStatusTableImpl extends CommentStatusTable with RootConnector {

  override lazy val tableName = "commentstatus"

  def save(commentStatus: CommentStatus): Future[ResultSet] = {
    insert
      .value(_.commentId, commentStatus.commentId)
      .value(_.status, commentStatus.status)
      .value(_.date, commentStatus.date)
      .future()
  }

  def getCommentStatus(commentsId: String): Future[Seq[CommentStatus]] = {
    select
      .where(_.commentId eqs commentsId)
      .fetchEnumerator() run Iteratee.collect()
  }

}
