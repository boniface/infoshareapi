package services.content

import repositories.content.ContentTypeRepository
import com.outworkers.phantom.dsl.ResultSet
import domain.content.ContentType
import javax.inject.Singleton

import scala.concurrent.Future

trait ContentTypeService extends ContentTypeRepository {

  def save(contentType: ContentType): Future[ResultSet] = {
    database.contentTypeTable.save(contentType)
  }

  def getById(id: String): Future[Option[ContentType]] = {
    database.contentTypeTable.getById(id)
  }

  def getAll: Future[Seq[ContentType]] = {
    database.contentTypeTable.getAll
  }

}
@Singleton
object ContentTypeService extends ContentTypeService with ContentTypeRepository
