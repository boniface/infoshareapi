package services.person

import com.datastax.driver.core.ResultSet
import domain.person.PersonLanguage

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/12/17.
 */
object PersonLanguageService extends Service {
  def saveOrUpdate(entity: PersonLanguage): Future[ResultSet] = {
    PersonLanguageRepository.save(entity)
  }

  def getValues(personId: String): Future[Seq[PersonLanguage]] = {
    PersonLanguageRepository.findPersonLanguages(personId)
  }

  def getPersonValue(personId: String, id: String): Future[Option[PersonLanguage]] = {
    PersonLanguageRepository.findById(personId, id)
  }

}
