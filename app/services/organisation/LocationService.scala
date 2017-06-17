package services.organisation

import com.outworkers.phantom.dsl.ResultSet
import javax.inject.Singleton
import domain.organisation.Location
import repositories.organisation.LocationRepository

import scala.concurrent.Future

trait LocationService extends LocationRepository {

  def save(obj: Location): Future[ResultSet] = {
    database.locationTable.save(obj)
  }

  def getById(map: Map[String, String]): Future[Option[Location]] = {
    database.locationTable.findById(map)
  }
  def getAll(org: String): Future[Seq[Location]] = {
    database.locationTable.findAll(org)
  }

  def delete(map: Map[String, String]): Future[ResultSet] = {
    database.locationTable.deleteById(map)
  }

}

@Singleton
object LocationService extends LocationService with LocationRepository
