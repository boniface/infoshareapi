package repositories.storage.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.storage.FileResults

import scala.concurrent.Future

abstract class FileResultsTable extends Table[FileResultsTable,FileResults] {

  object id extends StringColumn with PartitionKey

  object url extends StringColumn

  object size extends OptionalStringColumn

  object mime extends StringColumn

}


abstract class FileResultsTableImpl extends FileResultsTable with RootConnector {
  override lazy val tableName = "fileResults"

  def save(obj: FileResults): Future[ResultSet] = {
    insert
      .value(_.id, obj.id)
      .value(_.url, obj.url)
      .value(_.size, obj.size)
      .value(_.mime, obj.mime)
      .future()
  }

  def getById(id: String): Future[Option[FileResults]] = {
    select.where(_.id eqs id).one()
  }

  def getAll: Future[Seq[FileResults]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
}