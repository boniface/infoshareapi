package services.demographics

import com.outworkers.phantom.dsl.ResultSet
import domain.demographics.Gender
import repositories.demographics.GenderRepository

import scala.concurrent.Future


trait GenderService extends GenderRepository{

  def save(gender: Gender): Future[ResultSet] = {
    database.genderTable.save(gender)
  }

  def getById(id: String):Future[Option[Gender]] = {
    database.genderTable.findById(id)
  }
  def getAll: Future[Seq[Gender]] = {
    database.genderTable.findAll
  }

  def deleteById(id:String): Future[ResultSet] = {
    database.genderTable.deleteById(id)
  }

}

object GenderService extends GenderService with GenderRepository