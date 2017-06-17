package services.demographics

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.demographics.LanguageProficiency
import repositories.demographics.LanguageProficiencyRepository

import scala.concurrent.Future

trait LanguageProficiencyService extends LanguageProficiencyRepository {

  def save(obj: LanguageProficiency): Future[ResultSet] = {
    database.languageProficiencyTable.save(obj)
  }

  def getById(id: String): Future[Option[LanguageProficiency]] = {
    database.languageProficiencyTable.findById(id)
  }
  def getAll: Future[Seq[LanguageProficiency]] = {
    database.languageProficiencyTable.findAll
  }

  def deleteById(id: String): Future[ResultSet] = {
    database.languageProficiencyTable.deleteById(id)
  }

}

@Singleton
object LanguageProficiencyService extends LanguageProficiencyService with LanguageProficiencyRepository
