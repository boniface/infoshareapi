package services.demographics

import com.outworkers.phantom.dsl.ResultSet
import domain.demographics.Language
import repositories.demographics.LanguageRepository

import scala.concurrent.Future


trait LanguageService extends LanguageRepository {

  def save(obj : Language): Future[ResultSet] = {
    database.languageTable.save(obj)
  }

  def getById(id: String):Future[Option[Language]] = {
    database.languageTable.findById(id)
  }
  def getAll: Future[Seq[Language]] = {
    database.languageTable.findAll
  }

  def deleteById(id:String): Future[ResultSet] = {
    database.languageTable.deleteById(id)
  }

}

object LanguageService extends LanguageService with LanguageRepository
