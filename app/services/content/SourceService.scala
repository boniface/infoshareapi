package services.content

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.content.Source
import repositories.content.SourceRepository

import scala.concurrent.Future


trait SourceService extends SourceRepository{


  def save(obj : Source): Future[ResultSet] = {
    database.sourceTable.save(obj)
  }

  def getById(map: Map[String,String]): Future[Option[Source]] = {
    database.sourceTable.getById(map)
  }

  def getAll(org: String): Future[Seq[Source]] = {
    database.sourceTable.getAll(org)
  }

}
@Singleton
object SourceService extends SourceService with SourceRepository
