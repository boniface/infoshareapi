package services.zones

import com.datastax.driver.core.ResultSet
import domain.zones.Zone
import repositories.zones.ZoneRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ZoneService extends ZoneRepository {

  def SaveOrUpdate(zone: Zone): Future[ResultSet] = {
    for {
      saveEntity <- database.zoneTable.SaveOrUpdate(zone)
    } yield saveEntity
  }

  def getActiveZones:Future[Seq[Zone]] ={
    getAllZones.map( zones=> zones.filter(zone => zone.status.equals("ACTIVE")))
  }

  def getDisabledZones:Future[Seq[Zone]] ={
    getAllZones.map( zones=> zones.filter(zone => zone.status.equals("DISABLED")))
  }

  def getZoneById(code: String): Future[Option[Zone]] = {
    database.zoneTable.getZoneById(code)
  }

  def getAllZones: Future[Seq[Zone]] = {
    database.zoneTable.getAllZones
  }

  def deleteZoneById(code: String): Future[ResultSet] = {
    database.zoneTable.deleteZoneById(code)
  }
}

object ZoneService extends ZoneService with ZoneRepository