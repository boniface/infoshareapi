package services.users

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserAddress
import repositories.users.UserAddressRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


trait UserAddressService extends UserAddressRepository{

  def save(userAddr: UserAddress): Future[ResultSet] = {
    database.userAddressTable.save(userAddr)
  }

  def getAddrById(map: Map[String, String]): Future[Option[UserAddress]] =  {
    database.userAddressTable.findById(map)
  }

  def getUserAllAddress(emailId: String): Future[Seq[UserAddress]] = {
    database.userAddressTable.findAllAddress(emailId)
  }

}

object UserAddressService extends UserAddressService with UserAddressRepository
