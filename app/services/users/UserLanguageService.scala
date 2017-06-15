package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserLanguage
import repositories.users.UserLanguageRepository

import scala.concurrent.Future

trait UserLanguageService extends UserLanguageRepository {

  def save(userLanguage: UserLanguage): Future[ResultSet] = {
    database.userLanguageTable.save(userLanguage)
  }

  def getUserLangById(map: Map[String, String]): Future[Option[UserLanguage]] = {
    database.userLanguageTable.findById(map)
  }

  def getAllUserLang(emailId: String): Future[Seq[UserLanguage]] = {
    database.userLanguageTable.findUserLanguages(emailId)
  }

}
@Singleton
object UserLanguageService extends UserLanguageService with UserLanguageRepository
