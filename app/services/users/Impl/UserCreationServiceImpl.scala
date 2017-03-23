package services.users.Impl

import conf.security.Crediential
import domain.users.{User, UserRole}
import domain.util.Token
import services.users.{UserCreationService, UserRoleService, UserService}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by hashcode on 2017/02/27.
  */


class UserCreationServiceImpl extends UserCreationService {

  override def createUser(user: User, role: UserRole): Future[Boolean] = {

    val check = UserService.userNotAvailable(user.email)

    for {
      userAvailable <- UserService.userNotAvailable(user.email)
      createUser <- UserService.saveOrUpdate(user) if userAvailable
      createRole <- UserRoleService.save(role)
    } yield createUser.isExhausted
  }

  override def registerUser(user: User): Future[Boolean] = ???

  override def isUserRegistered(user: User): Future[Boolean] = {
    UserService.getSiteUser(user.email) map (user => user match {
      case Some(u) => true
      case None => false
    })
  }

  override def updateUser(user: User): Future[Boolean] = {
    Future {
      UserService.saveOrUpdate(user).isCompleted
    }
  }

  override def loginUser(credentials: Crediential): Future[Token] = ???

  override def getUser(email: String): Future[Option[User]] = {
    UserService.getSiteUser(email)
  }
}
