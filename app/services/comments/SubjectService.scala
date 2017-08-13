package services.comments

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.comments.Subject
import repositories.comments.SubjectRepository

import scala.concurrent.Future

/**
  * Created by hashcode
  */
trait SubjectService extends SubjectRepository {

  def save(obj: Subject): Future[ResultSet] = {
    database.subjectTable.save(obj)
  }

  def getSubjectById(siteId: String, subjectId: String): Future[Option[Subject]] = {
    database.subjectTable.getSubjectById(siteId, subjectId)
  }

  def getSiteSubjects(siteId: String): Future[Seq[Subject]] = {
    database.subjectTable.getSiteSubjects(siteId)
  }
}

@Singleton
object SubjectService extends SubjectService with SubjectRepository
