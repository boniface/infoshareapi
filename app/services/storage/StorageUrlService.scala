package services.storage

import com.outworkers.phantom.dsl.ResultSet
import domain.storage.StorageUrl
import repositories.storage.StorageUrlRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait StorageUrlService extends StorageUrlRepository{

  def save(link: StorageUrl): Future[ResultSet] = {
    for {
      linkEntity <- database.storageUrlTable.save(link)
    } yield linkEntity
  }

  def getLinkById(id: String): Future[Option[StorageUrl]] = {
    database.storageUrlTable.getLinkById(id)
  }

  def getAllLinks: Future[Seq[StorageUrl]] = {
    database.storageUrlTable.getAllLinks
  }
}

object StorageUrlService extends StorageUrlService with StorageUrlRepository