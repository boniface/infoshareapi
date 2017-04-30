package services.users

import com.outworkers.phantom.dsl.ResultSet
import domain.users.UserContact
import repositories.users.UserContactRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait UserContactService extends UserContactRepository {

  def save(userContact: UserContact): Future[ResultSet] = {
    for{
      saveUserContact <- database.userContactTable.save(userContact)
    } yield saveUserContact
  }

  def getById(map: Map[String, String]): Future[Option[UserContact]] = {
    database.userContactTable.findById(map)
  }

  def getAllUserContacts(emailId: String): Future[Seq[UserContact]] = {
    database.userContactTable.findUserContacts(emailId)
  }
}

object UserContactService extends UserContactService with UserContactRepository
