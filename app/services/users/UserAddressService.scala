package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserAddress
import repositories.users.UserAddressRepository

import scala.concurrent.Future

trait UserAddressService extends UserAddressRepository {

  def save(userAddr: UserAddress): Future[ResultSet] = {
    database.userAddressTable.save(userAddr)
  }

  def getById(map: Map[String, String]): Future[Option[UserAddress]] = {
    database.userAddressTable.getById(map)
  }

  def getAll(emailId: String): Future[Seq[UserAddress]] = {
    database.userAddressTable.getAll(emailId)
  }

}
@Singleton
object UserAddressService extends UserAddressService with UserAddressRepository
