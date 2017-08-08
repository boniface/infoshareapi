package repositories.comments.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.comments.Comment

import scala.concurrent.Future


/**
  * Created by Bonga on 10/28/2016.
  */


abstract class CommentTable  extends Table[CommentTable, Comment] {

  object siteId extends StringColumn with PartitionKey

  object subjectId extends StringColumn with PrimaryKey

  object commentId extends StringColumn with PrimaryKey

  object emailId extends StringColumn

  object ipaddress extends StringColumn

  object comment extends StringColumn

  object date extends Col[LocalDateTime]

}

abstract class  CommentTableImpl extends CommentTable with RootConnector {

  override lazy val tableName = "sitecomments"

  def save(comment: Comment): Future[ResultSet] = {
    insert
      .value(_.subjectId, comment.subjectId)
      .value(_.siteId, comment.siteId)
      .value(_.comment, comment.comment)
      .value(_.emailId, comment.emailId)
      .value(_.ipaddress, comment.ipaddress)
      .value(_.comment, comment.comment)
      .value(_.date, comment.date)
      .future()
  }


  def getSiteComments(siteId: String): Future[Seq[Comment]] = {
    select
      .where(_.siteId eqs siteId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getSubjectComments(siteId: String,subjectId:String): Future[Seq[Comment]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.subjectId eqs subjectId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getComment(siteId: String, subjectId: String, commentId:String): Future[Option[Comment]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.subjectId eqs subjectId)
      .and(_.commentId eqs commentId)
      .one()
  }
}

abstract class CommentsByUserTable extends Table[CommentsByUserTable, Comment] {

  object siteId extends StringColumn with PartitionKey

  object emailId extends StringColumn with PrimaryKey

  object subjectId extends StringColumn with PrimaryKey

  object commentId extends StringColumn with PrimaryKey

  object ipaddress extends StringColumn

  object comment extends StringColumn

  object date extends Col[LocalDateTime]


}

abstract class CommentsByUserTableImpl extends CommentsByUserTable with RootConnector {

  override lazy val tableName = "usercomments"

  def save(comment: Comment): Future[ResultSet] = {
    insert
      .value(_.subjectId, comment.subjectId)
      .value(_.siteId, comment.siteId)
      .value(_.comment, comment.comment)
      .value(_.emailId, comment.emailId)
      .value(_.ipaddress, comment.ipaddress)
      .value(_.comment, comment.comment)
      .value(_.date, comment.date)
      .future()
  }


  def getUserComments(siteId: String, emailId: String): Future[Seq[Comment]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.emailId eqs emailId)
      .fetchEnumerator() run Iteratee.collect()
  }
}

abstract class SingleCommentTable extends Table[SingleCommentTable, Comment] {

  object commentId extends StringColumn with PartitionKey

  object siteId extends StringColumn with PrimaryKey

  object subjectId extends StringColumn with PrimaryKey

  object emailId extends StringColumn

  object ipaddress extends StringColumn

  object comment extends StringColumn

  object date extends Col[LocalDateTime]

}

abstract class SingleCommentTableImpl extends SingleCommentTable with RootConnector {

  override lazy val tableName = "singlecomments"


  def save(comment: Comment): Future[ResultSet] = {
    insert
      .value(_.subjectId, comment.subjectId)
      .value(_.siteId, comment.siteId)
      .value(_.comment, comment.comment)
      .value(_.emailId, comment.emailId)
      .value(_.ipaddress, comment.ipaddress)
      .value(_.comment, comment.comment)
      .value(_.date, comment.date)
      .future()
  }

  def getComment(commentId: String): Future[Option[Comment]] = {
    select
      .where(_.commentId eqs commentId)
      .one()
  }
}

