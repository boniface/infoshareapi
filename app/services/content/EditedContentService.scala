package services.content

import com.outworkers.phantom.dsl.ResultSet
import domain.content.EditedContent
import repositories.content.EditedContentRepository
import javax.inject.Singleton

import scala.concurrent.Future

trait EditedContentService extends EditedContentRepository {

  def save(cont: EditedContent): Future[ResultSet] = {
    database.editedContentTable.save(cont)
  }
  def getById(map: Map[String, String]): Future[Option[EditedContent]] = {
    database.editedContentTable.getById(map)
  }

  def getAll(org: String): Future[Seq[EditedContent]] = {
    database.editedContentTable.getAll(org)
  }

  def getPaginatedContents(org:String, startValue: Int): Future[Iterator[EditedContent]] = {
    database.editedContentTable.getPaginatedContents(org, startValue)
  }

}

@Singleton
object EditedContentService extends EditedContentService with EditedContentRepository