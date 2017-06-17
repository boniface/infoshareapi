package services.storage

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.storage.FileResults
import repositories.storage.FileResultsRepository

import scala.concurrent.Future

trait FileResultsService extends FileResultsRepository {

  def save(obj: FileResults): Future[ResultSet] = {
    database.fileResultsTable.save(obj)
  }

  def getById(id: String): Future[Option[FileResults]] = {
    database.fileResultsTable.getById(id)
  }

  def getAll: Future[Seq[FileResults]] = {
    database.fileResultsTable.getAll
  }
}

@Singleton
object FileResultsService extends FileResultsService with FileResultsRepository
