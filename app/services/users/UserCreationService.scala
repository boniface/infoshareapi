package services.users

import conf.security.Crediential
import domain.users.{User, UserRole}
import domain.util.Token
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

  def loginUser(credentials: Crediential):Future[Token]

  def getUser(email:String):Future[Option[User]]
}

object UserCreationService{

  def apply: UserCreationService = new UserCreationServiceImpl()
}
