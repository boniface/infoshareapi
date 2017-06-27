package services.util

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.util.ItemStatus
import repositories.util.ItemStatusRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait ItemStatusService extends ItemStatusRepository {

  def save(status: ItemStatus): Future[ResultSet] = {
    database.itemStatusTable.save(status)
  }

  def getStatus(itemId: String): Future[Seq[ItemStatus]] = {
    database.itemStatusTable.getStatus(itemId)
  }

}

@Singleton
object ItemStatusService extends ItemStatusService with ItemStatusRepository
