package services.content

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.content.PublishedContent
import repositories.content.PublishedContentRepository

import scala.concurrent.Future

trait PublishedContentService extends PublishedContentRepository {

  def save(cont: PublishedContent): Future[ResultSet] = {
    database.publishedContentTable.save(cont)
  }
  def getContentById(map: Map[String,String]): Future[Option[PublishedContent]] = {
    database.publishedContentTable.getContentById(map)
  }

  def getAllContents(org: String): Future[Seq[PublishedContent]] = {
    database.publishedContentTable.getAllContents(org)
  }

  def getPaginatedContents(org: String, pageNum: Int): Future[Iterator[PublishedContent]] = {
    database.publishedContentTable.getPaginatedContents(org, pageNum)
  }

}

@Singleton
object PublishedContentService extends PublishedContentService with PublishedContentRepository
