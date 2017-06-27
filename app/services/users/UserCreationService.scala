package services.users

import domain.users.{User, UserRole}
import services.users.Impl.UserCreationServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/02/27.
  */


trait UserCreationService {

  def createUser(user: User, role:UserRole): Future[Boolean]

  def registerUser(user:User): Future[Boolean]

  def isUserRegistered(user:User): Future[Boolean]

  def updateUser(user:User): Future[Boolean]

  def getUser(email: String,siteId:String):Future[Option[User]]
}

object UserCreationService{

  def apply: UserCreationService = new UserCreationServiceImpl()
}
