package services.person

import com.outworkers.phantom.dsl.ResultSet
import domain.person.UserAddress
import repositories.person.UserAddressRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


trait UserAddressService extends UserAddressRepository{

  def save(userAddr: UserAddress): Future[ResultSet] = {
    for {
      saveEntity <- database.userAddressTable.save(userAddr)
    } yield saveEntity
  }

  def getAddrById(map: Map[String, String]): Future[Option[UserAddress]] =  {
    database.userAddressTable.findById(map)
  }

  def getUserAllAddress(userId: String): Future[Seq[UserAddress]] = {
    database.userAddressTable.findAllAddress(userId)
  }

}

object UserAddressService extends UserAddressService with UserAddressRepository
