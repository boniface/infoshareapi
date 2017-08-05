package repositories.comments.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.comments.Subject

import scala.concurrent.Future

/**
  * Created
  */

abstract class SubjectTable  extends Table[SubjectTable, Subject]{

  object siteId extends StringColumn with PartitionKey
  object subjectId extends StringColumn with PrimaryKey
  object name extends StringColumn
  object url extends StringColumn
  object date extends Col[LocalDateTime]


}

abstract class  SubjectTableImpl extends SubjectTable with RootConnector {

  override lazy val tableName = "subjects"

  def save(subject: Subject): Future[ResultSet] = {
    insert
      .value(_.subjectId, subject.subjectId)
      .value(_. siteId, subject.siteId)
      .value(_. name, subject.name)
      .value(_.url, subject.url)
      .value(_.date, subject.date)
      .future()
  }

  def getSubjectById(siteId: String, subjectId: String): Future[Option[Subject]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.subjectId eqs subjectId)
      .one()
  }

  def getSiteSubjects(siteId: String): Future[Seq[Subject]] = {
    select.where(_.siteId eqs siteId).fetchEnumerator() run Iteratee.collect()
  }
}
