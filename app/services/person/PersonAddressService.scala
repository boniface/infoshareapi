package services.person

import com.datastax.driver.core.ResultSet
import domain.person.PersonAddress

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/12/17.
 */
object PersonAddressService extends Service {

  def saveOrUpdate(entity: PersonAddress): Future[ResultSet] = {
    PersonAddressRepository.save(entity)
  }

  def getValues(personId: String): Future[Seq[PersonAddress]] = {
    PersonAddressRepository.findPersonAddresses(personId)
  }

  def getPersonValue(personId: String, id: String): Future[Option[PersonAddress]] = {
    PersonAddressRepository.findById(personId, id)
  }
}
