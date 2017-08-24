package services.comments

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.comments.Abuse
import repositories.comments.AbuseRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait AbuseService extends AbuseRepository {
  def save(obj: Abuse): Future[ResultSet] = {
    for {
      entity <- database.abuseTable.save(obj)
      entity <- database.abuseTable.save(obj)
    } yield entity
  }

  def getItemAbuse(siteId: String, commentIdOrResponseId: String): Future[Seq[Abuse]] = {
    database.abuseTable.getItemAbuse(siteId, commentIdOrResponseId)
  }
  def getUserAbusiveComments(siteId: String,emailId:String): Future[Seq[Abuse]] = {
    database.abuseByUserTable.getUserAbusiveComments(siteId, emailId)
  }
}

@Singleton
object AbuseService extends AbuseService with AbuseRepository
