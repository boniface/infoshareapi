package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.ValidUser
import repositories.users.ValidUserRepository

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/06/11.
  */
trait ValidUserService extends ValidUserRepository {

  def save(user: ValidUser): Future[ResultSet] = {
    database.validUserTable.save(user)

  }

  def isUserValid(userId: String): Future[Boolean] = {
    database.validUserTable.isUserValid(userId)
  }

  def getValidUserEvents(userId: String): Future[Seq[ValidUser]] = {
    database.validUserTable.getValidUserEvents(userId)
  }

  def getValidUsers: Future[Int] = {
    database.validUserTable.getValidUsers
  }

}

@Singleton
object ValidUserService extends ValidUserService with ValidUserRepository
