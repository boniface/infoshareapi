package repositories.storage.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.storage.StorageUrl

import scala.concurrent.Future

abstract class StorageUrlTable extends Table[StorageUrlTable, StorageUrl] {

  object id extends StringColumn with PartitionKey

  object name extends StringColumn

  object url extends StringColumn

}

abstract class StorageUrlTableImpl extends StorageUrlTable with RootConnector {
  override lazy val tableName = "storageUrl"

  def save(link: StorageUrl): Future[ResultSet] = {
    insert
      .value(_.id, link.id)
      .value(_.name, link.name)
      .value(_.url, link.url)
      .future()
  }

  def getAllLinks: Future[Seq[StorageUrl]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def getLinkById(id: String): Future[Option[StorageUrl]] = {
    select.where(_.id eqs id).one()
  }
}
