package services.organisation

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.organisation.Organisation
import repositories.organisation.OrganisationRepository

import scala.concurrent.Future

trait OrganisationService extends OrganisationRepository {
  def save(obj: Organisation): Future[ResultSet] = {
    database.organisationTable.save(obj)
  }

  def getById(id: String): Future[Option[Organisation]] = {
    database.organisationTable.getById(id)
  }
  def getAll: Future[Seq[Organisation]] = {
    database.organisationTable.getAll
  }

}

@Singleton
object OrganisationService extends OrganisationService with OrganisationRepository
