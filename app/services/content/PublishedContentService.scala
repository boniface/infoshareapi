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
  def getById(map: Map[String,String]): Future[Option[PublishedContent]] = {
    database.publishedContentTable.getById(map)
  }

  def getAll(org: String): Future[Seq[PublishedContent]] = {
    database.publishedContentTable.getAll(org)
  }

  def getPaginatedContents(org: String, pageNum: Int): Future[Iterator[PublishedContent]] = {
    database.publishedContentTable.getPaginatedContents(org, pageNum)
  }

}

@Singleton
object PublishedContentService extends PublishedContentService with PublishedContentRepository
