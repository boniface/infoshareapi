package services.demographics

import com.outworkers.phantom.dsl.ResultSet
import domain.demographics.Language
import repositories.demographics.LanguageRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait LanguageService extends LanguageRepository {

  def save(obj : Language): Future[ResultSet] = {
    for {
      saveEntity <- database.languageTable.save(obj)
    } yield saveEntity
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
