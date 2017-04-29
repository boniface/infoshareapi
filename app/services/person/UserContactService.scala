package services.person

import com.outworkers.phantom.dsl.ResultSet
import domain.person.UserContact
import repositories.person.UserContactRepository

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

  def getAllUserContacts(userId: String): Future[Seq[UserContact]] = {
    database.userContactTable.findUserContacts(userId)
  }
}

object UserContactService extends UserContactService with UserContactRepository
