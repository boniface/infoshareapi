package services.location

import com.outworkers.phantom.dsl.ResultSet
import domain.location.AddressType
import repositories.location.AddressTypeRepository

import scala.concurrent.Future


trait AddressTypeService extends AddressTypeRepository {

  def save(addr: AddressType): Future[ResultSet] = {
    database.addressTypeTable.save(addr)
  }

  def getAddressById(id: String):Future[Option[AddressType]] = {
    database.addressTypeTable.findById(id)
  }
  def getAllAddresses: Future[Seq[AddressType]] = {
    database.addressTypeTable.findAll
  }

  def deleteById(id:String): Future[ResultSet] = {
    database.addressTypeTable.deleteById(id)
  }

}

object AddressTypeService extends AddressTypeService with AddressTypeRepository
