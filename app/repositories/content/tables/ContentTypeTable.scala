package repositories.content.tables


import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.content.ContentType

import scala.concurrent.Future

class ContentTypeTable extends CassandraTable[ContentTypeTable, ContentType] {

  object id extends StringColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object description extends StringColumn(this)

}

abstract  class ContentTypeTableImpl extends ContentTypeTable with RootConnector {
  override lazy val tableName = "contentTypes"

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

