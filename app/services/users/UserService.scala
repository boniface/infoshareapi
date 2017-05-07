package services.users

import com.outworkers.phantom.dsl.ResultSet
import domain.users.User
import repositories.users.UserRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


trait UserService extends UserRepository {

  def save(user: User): Future[ResultSet] = {
    for {
      saveEntity <- database.userTable.save(user)
      saveEntity <- database.personTable.save(user)
    } yield saveEntity
  }

  def getUser(org: String, email: String): Future[Option[User]] = {
    database.userTable.getUser(org, email)
  }

  def getUsers(org: String): Future[Seq[User]] = {
    database.userTable.getUsers(org)
  }

  def getUserByEmail(email: String): Future[Seq[User]] = {
    database.personTable.getUserByEmail(email)
  }
}

object UserService extends UserService with UserRepository
