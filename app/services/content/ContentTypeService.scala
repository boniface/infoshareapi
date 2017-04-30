package services.content

import repositories.content.ContentTypeRepository
import com.outworkers.phantom.dsl.ResultSet
import domain.content.ContentType

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future



trait ContentTypeService extends ContentTypeRepository{

  def save(contentType: ContentType): Future[ResultSet] = {
    for {
      contentType <- database.contentTypeTable.save(contentType)
    } yield contentType
  }

  def getContentTypeById(id: String): Future[Option[ContentType]] = {
    database.contentTypeTable.getContentTypeById(id)
  }

  def getAllContentTypes: Future[Seq[ContentType]] = {
    database.contentTypeTable.getAllContentTypes
  }

}

object ContentTypeService extends ContentTypeService with ContentTypeRepository