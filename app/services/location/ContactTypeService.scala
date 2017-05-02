package services.location

import com.outworkers.phantom.dsl.ResultSet
import domain.location.ContactType
import repositories.location.ContactTypeRepository

import scala.concurrent.Future


trait ContactTypeService extends ContactTypeRepository{

  def save(obj : ContactType): Future[ResultSet] = {
    database.contactTypeTable.save(obj)
  }

  def getAddressById(id: String):Future[Option[ContactType]] = {
    database.contactTypeTable.findById(id)
  }
  def getAllAddresses: Future[Seq[ContactType]] = {
    database.contactTypeTable.findAll
  }

  def deleteById(id:String): Future[ResultSet] = {
    database.contactTypeTable.deleteById(id)
  }

}

object ContactTypeService extends ContactTypeService with ContactTypeRepository
