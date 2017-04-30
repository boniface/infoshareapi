package services.demographics


import com.outworkers.phantom.dsl.ResultSet
import domain.demographics.Race
import repositories.demographics.RaceRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait RaceService extends RaceRepository {

  def save(race: Race): Future[ResultSet] = {
    for {
      saveGender <- database.raceTable.save(race)
    } yield saveGender
  }

  def getById(id: String):Future[Option[Race]] = {
    database.raceTable.findById(id)
  }
  def getAll: Future[Seq[Race]] = {
    database.raceTable.findAll
  }

  def deleteById(id:String): Future[ResultSet] = {
    database.raceTable.deleteById(id)
  }

}

object RaceService extends RaceService with RaceRepository