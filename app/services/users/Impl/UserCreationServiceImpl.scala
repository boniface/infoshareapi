package services.users.Impl

import java.time.LocalDateTime
import java.util.UUID

import conf.util.{UserEvents, Util, MailEvents}
import domain.syslog.SystemLogEvents
import domain.users.{User, UserRole}
import services.mail.SendMailService
import services.syslog.SyslogService
import services.users.{UserCreationMessageService, UserCreationService, UserRoleService, UserService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class UserCreationServiceImpl extends UserCreationService {

  override def createUser(user: User, role: UserRole): Future[Boolean] = {
    val message: (String, User) = UserCreationMessageService.apply.accountCreatedMessage(user)
    val subject = "New Account Created for " + user.email
    val successLog = SystemLogEvents(
      user.email,
      Util.md5Hash(UUID.randomUUID().toString),
      MailEvents.MAIL_SENT,
      MailEvents.MAIL,
      " Mail Successfully Sent",
      LocalDateTime.now())

    val failLog = SystemLogEvents(
      user.siteId,
      Util.md5Hash(UUID.randomUUID().toString),
      UserEvents.CREATIONFAILED,
      UserEvents.ACCOUNT_EXIST,
      " Duplicate Account," + user.email + " Account Already Exist",
      LocalDateTime.now())

    val check: Future[Boolean] = UserService.userNotAvailable(user.email,user.siteId)

    val results = check map (isAvailable => {
      if (isAvailable) {
        for {
          createUser <- UserService.saveOrUpdate(message._2)
          createRole <- UserRoleService.save(role)
          send <- SendMailService.apply.send(message._2, subject, message._1)
          log <- SyslogService.save(successLog)
        } yield createUser.isExhausted

      } else {
        Future {
          for {
            log <- SyslogService.save(failLog)
          } yield log
          false
        }
      }
    })
    results.flatten
  }

  override def registerUser(user: User): Future[Boolean] = ???

  override def isUserRegistered(user: User): Future[Boolean] = {
    UserService.getSiteUser(user.email,user.siteId) map {
      case Some(_) => true
      case None => false
    }
  }

  override def updateUser(user: User): Future[Boolean] = {
    Future {
      UserService.saveOrUpdate(user).isCompleted
    }
  }

  override def getUser(email: String,siteId:String): Future[Option[User]] = {
    UserService.getSiteUser(email,siteId )
  }
}
