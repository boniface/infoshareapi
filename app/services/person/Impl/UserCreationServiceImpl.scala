package services.person.Impl

import conf.security.Crediential
import domain.person.{User, UserRole}
import domain.util.Token
import services.person.UserCreationService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Created by hashcode on 2017/02/27.
  */


class UserCreationServiceImpl extends UserCreationService {

  override def createUser(user: User, role: UserRole): Future[Boolean] = {

  ???
  }

  override def registerUser(user: User): Future[Boolean] = ???

  override def isUserRegistered(user: User): Future[Boolean] = {
    ???
  }

  override def updateUser(user: User): Future[Boolean] = {
    Future {
     ???
    }
  }

  override def loginUser(credentials: Crediential): Future[Token] = ???

  override def getUser(email: String): Future[Option[User]] = {
    ???
  }
}
