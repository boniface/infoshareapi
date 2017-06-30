package services.users

import java.time.LocalDateTime

import com.outworkers.phantom.dsl.ResultSet
import domain.security.UserState
import domain.users.User
import repositories.users.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait UserService extends UserRepository {

  def saveOrUpdate(user: User): Future[ResultSet] = {
    for {
      saveEntity <- database.userTable.save(user)
      saveEntity <- database.siteUserTable.save(user)
      saveEntity <- database.userTimeLineTable.save(user)
    } yield saveEntity
  }

  def getSiteUser(email: String, siteId:String): Future[Option[User]] = {
    database.userTable.getUser(email,siteId)
  }

  def getUserAccounts(email: String):Future[Seq[User]] = {
    database.userTable.getUserAccounts(email)
  }

  def hasUserConfirmedAddress(email: String,siteId:String): Future[Boolean] = {
    getSiteUser(email,siteId) map (user => user.get.state == UserState.CONFIRMED)
  }

  def userNotAvailable(email: String,siteId:String): Future[Boolean] = {
    val user = database.userTable.getUser(email,siteId)
    user map (account => extractUser(account).email.equals(""))
  }


  def getSiteUsers(siteId: String): Future[Seq[User]] = {
    database.siteUserTable.getSiteUsers(siteId)
  }

  def getConfirmedSiteUsers(siteId: String): Future[Seq[User]] = {
    database.siteUserTable.getSiteUsers(siteId) map (users => users filter (user => user.state == UserState.CONFIRMED))
  }

  def getUnconfirmedSiteUsers(siteId: String): Future[Seq[User]] = {
    database.siteUserTable.getSiteUsers(siteId) map (users => users filter (user => user.state == UserState.UNCONFIRMED))
  }

  def deleteUser(email: String,siteId:String): Future[ResultSet] = {
    for {
      user <- database.userTable.getUser(email,siteId)
      deleteFromUsers <- database.userTable.deleteUser(extractUser(user).email,siteId)
      deleteUserFromSite <- database.siteUserTable.deleteUser(extractUser(user).siteId, extractUser(user).email)
      deleteUserFromTimeLine <- database.userTimeLineTable.deleteUser(extractUser(user).date, extractUser(user).siteId, extractUser(user).email)
      deleteUserRole <- UserRoleService.deleteUserRoles(extractUser(user))
    } yield deleteUserRole
  }

  def getUsersAccountsOlderThanOneDay: Future[Seq[User]] = {
    database.userTimeLineTable.getUsersAccountsOlderThanOneDay
  }

  def getUsersCreateAfterPeriod(date: LocalDateTime): Future[Seq[User]] = {
    database.userTimeLineTable.getUsersCreateAfterPeriod(date)

  }


  def extractUser(user: Option[User]): User = {
    user match {
      case Some(userValue) => userValue
      case None => User.identity
    }
  }
}

object UserService extends UserService with UserRepository
