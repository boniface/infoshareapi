package services.location

import com.outworkers.phantom.dsl.ResultSet
import domain.location.ContactType
import repositories.location.ContactTypeRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait ContactTypeService extends ContactTypeRepository{

  def save(obj : ContactType): Future[ResultSet] = {
    for {
      saveEntity <- database.contactTypeTable.save(obj)
    } yield saveEntity
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
