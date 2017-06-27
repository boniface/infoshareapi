package services.demographics

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.demographics.MaritalStatus
import repositories.demographics.MaritalStatusRepository

import scala.concurrent.Future

trait MaritalStatusService extends MaritalStatusRepository {

  def save(obj: MaritalStatus): Future[ResultSet] = {
    database.maritalStatusTable.save(obj)
  }

  def getById(id: String): Future[Option[MaritalStatus]] = {
    database.maritalStatusTable.getRoleById(id)
  }
  def getAll: Future[Seq[MaritalStatus]] = {
    database.maritalStatusTable.getRoles
  }

  def delete(id: String): Future[ResultSet] = {
    database.maritalStatusTable.deleteById(id)
  }

}

@Singleton
object MaritalStatusService extends MaritalStatusService with MaritalStatusRepository
