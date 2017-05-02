package services.content

import com.outworkers.phantom.dsl.ResultSet
import domain.content.RawContent
import repositories.content.RawContentRepository

import scala.concurrent.Future


trait RawContentService extends RawContentRepository{

  def save(cont : RawContent): Future[ResultSet] = {
    database.rawContentTable.save(cont)
  }
  def getContentById(map: Map[String,String]): Future[Option[RawContent]] = {
    database.rawContentTable.getContentById(map)
  }

  def getAllContents(org: String): Future[Seq[RawContent]] = {
    database.rawContentTable.getAllContents(org)
  }

  def getContents(org:String, startValue: Int): Future[Iterator[RawContent]] = {
    database.rawContentTable.getContents(org, startValue)
  }

}

object RawContentService extends RawContentService with RawContentRepository