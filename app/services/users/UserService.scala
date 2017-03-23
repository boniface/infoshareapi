package services.users

import com.datastax.driver.core.ResultSet
import domain.users.{User, UserState}
import repositories.users.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by hashcode on 2017/01/29.
  */
trait UserService extends UserRepository {

  def saveOrUpdate(user: User): Future[ResultSet] = {
    for {
      saveEntity <- database.userTable.save(user)
      saveEntity <- database.siteUserTable.save(user)
    } yield saveEntity
  }

  def getSiteUser(email: String): Future[Option[User]] = {
    database.userTable.getUser(email)
  }

  def hasUserConfirmedAddress(email:String):Future[Boolean] ={
    getSiteUser(email) map(user => user.get.state==UserState.CONFIRMED)
  }

  def userNotAvailable(email:String):Future[Boolean]={
    val user = database.userTable.getUser(email)
    user map (account => extractUser(account).email.equals("") )
  }


  def getSiteUsers(siteId: String): Future[Seq[User]] = {
    database.siteUserTable.getSiteUsers(siteId)
  }

  def getConfirmedSiteUsers(siteId: String): Future[Seq[User]] = {
    database.siteUserTable.getSiteUsers(siteId) map( users => users filter(user => user.state==UserState.CONFIRMED))
  }

  def getUnconfirmedSiteUsers(siteId: String): Future[Seq[User]] = {
    database.siteUserTable.getSiteUsers(siteId) map( users => users filter(user => user.state==UserState.UNCONFIRMED))
  }

  def deleteUser(email: String): Future[ResultSet] = {
    for {
      user <- database.userTable.getUser(email)
      delete <- database.userTable.deleteUser(extractUser(user).email)
      delete <- database.siteUserTable.deleteUser(extractUser(user).siteId,extractUser(user).email)
    } yield delete
  }

  def extractUser(user: Option[User]):User = {
    user match {
      case Some(userValue) => userValue
      case None => User.zero
    }
  }

}


object UserService extends UserService with UserRepository
