package services.demographics

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.demographics.Race
import repositories.demographics.RaceRepository

import scala.concurrent.Future

trait RaceService extends RaceRepository {

  def save(race: Race): Future[ResultSet] = {
    database.raceTable.save(race)
  }

  def getById(id: String): Future[Option[Race]] = {
    database.raceTable.findById(id)
  }
  def getAll: Future[Seq[Race]] = {
    database.raceTable.findAll
  }

  def deleteById(id: String): Future[ResultSet] = {
    database.raceTable.deleteById(id)
  }

}

@Singleton
object RaceService extends RaceService with RaceRepository
