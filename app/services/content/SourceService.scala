package services.content

import com.outworkers.phantom.dsl.ResultSet
import domain.content.Source
import repositories.content.SourceRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait SourceService extends SourceRepository{


  def save(obj : Source): Future[ResultSet] = {
    for{
      sourceEntity <- database.sourceTable.save(obj)
    } yield sourceEntity
  }

  def getSourceById(map: Map[String,String]): Future[Option[Source]] = {
    database.sourceTable.getSourceById(map)
  }

  def getAllSources(org: String): Future[Seq[Source]] = {
    database.sourceTable.getAllSources(org)
  }

}

object SourceService extends SourceService with SourceRepository
