package services.users

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.users.ValidUser
import repositories.users.ValidUserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait ValidUserService extends ValidUserRepository {

  def save(user: ValidUser): Future[ResultSet] = {
    for {
      _ <- database.validUserTable.save(user)
      saveValidUser <- database.timeLineValidUserTable.save(user)
    } yield saveValidUser

  }

  def isUserValid(siteId:String, userId: String): Future[Boolean] = {
    database.validUserTable.isUserValid(siteId,userId)
  }

  def getValidUserEvents(siteId:String,userId: String): Future[Seq[ValidUser]] = {
    database.validUserTable.getValidUserEvents(siteId,userId)
  }

  def getValidUsers: Future[Int] = {
    database.validUserTable.getValidUsers
  }

  def getValidUsersInLast24hours(siteId:String): Future[Seq[ValidUser]] = {
    database.timeLineValidUserTable.getValidUsersInLast24hours(siteId)
  }

}

@Singleton
object ValidUserService extends ValidUserService with ValidUserRepository
