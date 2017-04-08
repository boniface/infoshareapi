package services.person

import com.datastax.driver.core.ResultSet
import domain.person.PersonDemographics

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/12/17.
 */
object PersonDemographicsService extends Service{
  def saveOrUpdate(entity: PersonDemographics): Future[ResultSet] = {
    PersonDemographicsRepository.save(entity)
  }


  def getValues(personId: String): Future[Seq[PersonDemographics]] = {
    PersonDemographicsRepository.findPersonDemographics(personId)
  }

  def getPersonValue(personId: String, id: String): Future[Option[PersonDemographics]] = {
    PersonDemographicsRepository.findById(personId, id)
  }

}
