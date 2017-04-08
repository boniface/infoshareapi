package services.person

import com.datastax.driver.core.ResultSet
import domain.person.PersonImages

import scala.concurrent.Future

/**
 * Created by hashcode on 2016/01/03.
 */
object PersonImagesService {
  def save(image: PersonImages): Future[ResultSet] = {
    PersonImagesRepository.save(image)
  }

  def getPersonImage(company: String, personId: String, id: String): Future[Option[PersonImages]] = {
    PersonImagesRepository.getPersonImage(company, personId, id)
  }

  def getPersonImages(company: String, personId: String): Future[Seq[PersonImages]] = {
    PersonImagesRepository.getPersonImages(company, personId)
  }

  def getCompanyPeopleImages(company: String): Future[Seq[PersonImages]] = {
    PersonImagesRepository.getCompanyPeopleImages(company)
  }

}
