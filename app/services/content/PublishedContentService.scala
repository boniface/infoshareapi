package services.content

import com.outworkers.phantom.dsl.ResultSet
import domain.content.PublishedContent
import repositories.content.PublishedContentRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait PublishedContentService extends PublishedContentRepository{

  def save(cont : PublishedContent): Future[ResultSet] = {
    for{
      rawContent <- database.publishedContentTable.save(cont)
    } yield rawContent
  }
  def getContentById(map: Map[String,String]): Future[Option[PublishedContent]] = {
    database.publishedContentTable.getContentById(map)
  }

  def getAllContents(org: String): Future[Seq[PublishedContent]] = {
    database.publishedContentTable.getAllContents(org)
  }

  def getContents(org:String, startValue: Int): Future[Iterator[PublishedContent]] = {
    database.publishedContentTable.getContents(org, startValue)
  }

}

object PublishedContentService extends PublishedContentService with PublishedContentRepository