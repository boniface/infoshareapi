package services.content

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.content.RawContent
import repositories.content.RawContentRepository

import scala.concurrent.Future

trait RawContentService extends RawContentRepository {

  def save(cont: RawContent): Future[ResultSet] = {
    database.rawContentTable.save(cont)
  }
  def getById(map: Map[String, String]): Future[Option[RawContent]] = {
    database.rawContentTable.getById(map)
  }

  def getAll(org: String): Future[Seq[RawContent]] = {
    database.rawContentTable.getAll(org)
  }

  def getPaginatedContents(org:String, startValue: Int): Future[Iterator[RawContent]] = {
    database.rawContentTable.getPaginatedContents(org, startValue)
  }

}
@Singleton
object RawContentService extends RawContentService with RawContentRepository
