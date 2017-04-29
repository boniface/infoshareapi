package services.person

import com.outworkers.phantom.dsl.ResultSet
import domain.person.UserLanguage
import repositories.person.UserLanguageRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


trait UserLanguageService extends UserLanguageRepository{

  def save(userLanguage: UserLanguage): Future[ResultSet] = {
    for{
      saveEntity <- database.userLanguageTable.save(userLanguage)
    } yield saveEntity
  }

  def getUserLangById(map: Map[String, String]): Future[Option[UserLanguage]] = {
    database.userLanguageTable.findById(map)
  }

  def getAllUserLang(userId: String): Future[Seq[UserLanguage]] = {
    database.userLanguageTable.findUserLanguages(userId)
  }

}

object UserLanguageService extends UserLanguageService with UserLanguageRepository
