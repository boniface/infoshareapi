package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserContact
import repositories.users.UserContactRepository

import scala.concurrent.Future

trait UserContactService extends UserContactRepository {

  def save(userContact: UserContact): Future[ResultSet] = {
    database.userContactTable.save(userContact)
  }

  def getById(map: Map[String, String]): Future[Option[UserContact]] = {
    database.userContactTable.findById(map)
  }

  def getAllUserContacts(emailId: String): Future[Seq[UserContact]] = {
    database.userContactTable.findUserContacts(emailId)
  }
}
@Singleton
object UserContactService extends UserContactService with UserContactRepository
