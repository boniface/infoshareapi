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

  def getContentTypeById(id: String): Future[Option[ContentType]] = {
    database.contentTypeTable.getContentTypeById(id)
  }

  def getAllContentTypes: Future[Seq[ContentType]] = {
    database.contentTypeTable.getAllContentTypes
  }

}
@Singleton
object ContentTypeService extends ContentTypeService with ContentTypeRepository
