package repositories.content.tables


import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.content.ContentType

import scala.concurrent.Future

abstract class ContentTypeTable extends Table[ContentTypeTable, ContentType] {

  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object description extends StringColumn

}

abstract  class ContentTypeTableImpl extends ContentTypeTable with RootConnector {
  override lazy val tableName = "contentType"

  def save(contentType: ContentType): Future[ResultSet] = {
    insert
      .value(_.id,contentType.id)
      .value(_.name, contentType.name)
      .value(_.description, contentType.description)
      .future()
  }

  def getContentTypeById(id: String): Future[Option[ContentType]] = {
    select.where(_.id eqs id).one()
  }

  def getAllContentTypes: Future[Seq[ContentType]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
}

