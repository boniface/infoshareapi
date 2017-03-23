package services.zones

import com.datastax.driver.core.ResultSet
import domain.zones.Site
import repositories.zones.SiteRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait SiteService extends SiteRepository {

  def save(site: Site):Future[ResultSet]  = {
    for {
      saveEntity <- database.siteTable.save(site)
    } yield saveEntity
  }

  def getSiteById(zone: String, site: String): Future[Option[Site]] = {
    database.siteTable.getSiteById(zone,site)
  }

  def deleteSiteById(zone:String,site: String): Future[ResultSet] = {
    database.siteTable.deleteSiteById(zone,site)
  }

  def getSitesByNumber(start: Int, limit: Int): Future[Iterator[Site]] = {
    database.siteTable.getSitesByNumber(start,limit)

  }

  def getSitesByZone(zone: String): Future[Seq[Site]] = {
    database.siteTable.getSitesByZone(zone)
  }
}

object SiteService extends SiteService with SiteRepository