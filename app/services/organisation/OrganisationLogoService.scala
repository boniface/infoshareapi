package services.organisation

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.organisation.OrganisationLogo
import repositories.organisation.OrganisationLogoRepository

import scala.concurrent.Future

trait OrganisationLogoService extends OrganisationLogoRepository {
  def save(obj: OrganisationLogo): Future[ResultSet] = {
    database.organisationLogoTable.save(obj)
  }

  def getById(map: Map[String, String]): Future[Option[OrganisationLogo]] = {
    database.organisationLogoTable.getById(map)
  }
  def getAll(org: String): Future[Seq[OrganisationLogo]] = {
    database.organisationLogoTable.getAll(org)
  }

}

@Singleton
object OrganisationLogoService extends OrganisationLogoService with OrganisationLogoRepository
