package services.util

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.util.Keys
import repositories.util.KeysRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait KeysService extends KeysRepository {

  def save(key: Keys): Future[ResultSet] = {
    database.keysTable.save(key)
  }

  def getKeyById(id: String): Future[Option[Keys]] = {
    database.keysTable.getKeyById(id)
  }

  def getAllkeys: Future[Seq[Keys]] = {
    database.keysTable.getAllkeys
  }
}

@Singleton
object KeysService extends KeysService with KeysRepository
