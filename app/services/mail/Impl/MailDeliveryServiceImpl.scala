package services.mail.Impl

import java.time.LocalDateTime
import java.util.UUID

import views.html.email.new_login_details
import conf.util.{HashcodeKeys, MailEvents, Util}
import domain.syslog.SystemLogEvents
import domain.users.User
import domain.util.{EmailMessage, SmtpConfig}
import services.mail.{MailDeliveryService, MailService}
import services.security.AuthenticationService
import services.syslog.SyslogService
import services.users.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


@Deprecated
class MailDeliveryServiceImpl extends MailDeliveryService {

  override def sendMail(user: User): Future[String] = {
    val mailer = MailService.getMailer(HashcodeKeys.MAILORG)

    val result = mailer map (_ => {

      val updateUser = setUserPassword(user)
      val subject = "New Login Credentials "
      val message = new_login_details.render(updateUser._1.copy(password = updateUser._2))
      getMailResp(message.toString(),subject, user)
    })
    result flatMap  { response => response}
  }

  override def resetAccount(user: User): Future[String] = {

    val mailer = MailService.getMailer(HashcodeKeys.MAILORG)

    val result = mailer map (_ => {
      val updateUser = setUserPassword(user)
      val subject = "Your Reset New Login Credentials "
      val message = new_login_details.render(updateUser._1.copy(password = updateUser._2))
      getMailResp(message.toString(), subject, user)
    })
    result flatMap  { response => response}
  }


  override def sendMail(message: String, subject: String, user: User): Future[String] = {

    val mailer = MailService.getMailer(HashcodeKeys.MAILORG)
    mailer map (mail=> {
      val smtp = SmtpConfig(port = mail.port.toInt, host = mail.host, user = mail.key, password = mail.value)
      MailerService.send(EmailMessage(subject, user.email, mail.key, message, message, smtp)) match {
        case Success(_) => MailEvents.MAIL_SENT
        case Failure(_) => MailEvents.MAIL_SENT_FAILED
      }
    })
  }

  def getMailResp(message: String, subject: String, user: User): Future[String] = {
    sendMail(message, subject, user) map (result => {
      if (result == MailEvents.MAIL_SENT) {
        val successEvent = SystemLogEvents(
          user.siteId,
          Util.md5Hash(UUID.randomUUID().toString),
          MailEvents.MAIL,
          MailEvents.MAIL_SENT, MailEvents.MAIL_SENT, LocalDateTime.now())
        SyslogService.save(successEvent)
        MailEvents.MAIL_SENT
      } else {
        val failEvent = SystemLogEvents(
          user.siteId,
          Util.md5Hash(UUID.randomUUID().toString),
          MailEvents.MAIL,
          MailEvents.MAIL_SENT_FAILED, MailEvents.MAIL_SENT_FAILED, LocalDateTime.now())
        SyslogService.save(failEvent)
        MailEvents.MAIL_SENT_FAILED
      }
    })
  }

  def setUserPassword(user: User): (User, String) = {
    val passwd = AuthenticationService.apply.generateRandomPassword()
    val updatePerson = user.copy(password = AuthenticationService.apply.getHashedPassword(passwd))
    UserService.saveOrUpdate(updatePerson)
    (updatePerson, passwd)
  }
}
