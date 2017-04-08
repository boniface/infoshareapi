package services.person

import com.datastax.driver.core.ResultSet
import domain.person.PersonContact

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/12/17.
 */
object PersonContactService extends Service{
  def saveOrUpdate(entity: PersonContact): Future[ResultSet] = {
    PersonContactRepository.save(entity)
  }

  def getValues(personId: String): Future[Seq[PersonContact]] = {
    PersonContactRepository.findPersonContacts(personId)
  }

  def getPersonValue(personId: String, id: String): Future[Option[PersonContact]] = {
    PersonContactRepository.findById(personId, id)
  }

}
