package services.mail.Impl

import java.time.LocalDateTime
import java.util.UUID

import conf.util.{HashcodeKeys, Util}
import domain.syslog.SystemLogEvents
import domain.users.User
import domain.util.{EmailMessage, MailEvents, SmtpConfig}
import services.mail.{MailDeliveryService, MailService}
import services.security.AuthenticationService
import services.syslog.SyslogService
import services.users.UserService

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 6/24/17.
  */
class MailDeliveryServiceImpl extends MailDeliveryService{

  override def sendMail(user: User): Future[String] = {

    val mailer = MailService.getMailer(HashcodeKeys.MAILORG)
    val result = mailer map (mail => {

      val passwd = AuthenticationService.apply.generateRandomPassword()
      val updatePerson = user.copy(password = AuthenticationService.apply.getHashedPassword(passwd))
      UserService.saveOrUpdate(updatePerson)
      val subject = "New Login Credentials "
      val message = "Your Login Details are Username: " + updatePerson.email + " And the Password: " + passwd + "" +
        "</p> You can access the Site  Provided to you By the Provider. " +
        "<b>PLEASE REMEMBER TO CHANGE YOUR PASSWORD</b><p/>" +
        "We are Sure your Superiors have told you that Great Powers Come with Great Responsibility"

      sendMail(message, subject, user) map (result => {
        if (result == MailEvents.MAIL_SENT) {
          val successEvent = SystemLogEvents(
            user.org,
            Util.md5Hash(UUID.randomUUID().toString),
            MailEvents.MAIL,
            MailEvents.MAIL_SENT, MailEvents.MAIL_SENT, LocalDateTime.now())
          SyslogService.save(successEvent)
          MailEvents.MAIL_SENT
        } else {
          val failEvent = SystemLogEvents(
            user.org,
            Util.md5Hash(UUID.randomUUID().toString),
            MailEvents.MAIL,
            MailEvents.MAIL_SENT_FAILED, MailEvents.MAIL_SENT_FAILED, LocalDateTime.now())
          SyslogService.save(failEvent)
          MailEvents.MAIL_SENT_FAILED
        }
      })
    })
    result flatMap  { response => response}
  }

  override def resetAccount(user: User): Future[String] = {


    val mailer = MailService.getMailer(HashcodeKeys.MAILORG)

    val result = mailer map (mail => {

      val passwd = AuthenticationService.apply.generateRandomPassword()
      val updatePerson = user.copy(password = AuthenticationService.apply.getHashedPassword(passwd))
      UserService.saveOrUpdate(updatePerson)
      val subject = "Your Reset New Login Credentials "

      val message = "Your New Login Details are Username: " + updatePerson.email + " And the Password: " + passwd + "" +
        "</p> You can access the Site  Provided to you By the Provider. " +
        "<b>PLEASE REMEMBER TO CHANGE YOUR PASSWORD</b><p/>" +
        "We are Sure your Superiors have told you that Great Powers Come with Great Responsibility"

      sendMail(message, subject, user) map (result => {
        if (result == MailEvents.MAIL_SENT) {
          val successEvent = SystemLogEvents(
            user.org,
            Util.md5Hash(UUID.randomUUID().toString),
            MailEvents.MAIL,
            MailEvents.MAIL_SENT, MailEvents.MAIL_SENT, LocalDateTime.now())
          SyslogService.save(successEvent)
          MailEvents.MAIL_SENT
        } else {
          val failEvent = SystemLogEvents(
            user.org,
            Util.md5Hash(UUID.randomUUID().toString),
            MailEvents.MAIL,
            MailEvents.MAIL_SENT_FAILED, MailEvents.MAIL_SENT_FAILED, LocalDateTime.now())
          SyslogService.save(failEvent)
          MailEvents.MAIL_SENT_FAILED
        }
      })
    })
    result flatMap  { response => response}
  }


  override def sendMail(message: String, subject: String, user: User): Future[String] = {

    val mailer = MailService.getMailer(HashcodeKeys.MAILORG)

    mailer map (mail=> {
      val smtp = SmtpConfig(port = mail.port.toInt, host = mail.host, user = mail.key, password = mail.value)

      val msg = "<html>" +
        "<body>" +
        "<h2><u>The Message Content</u></h2>" +
        "Dear " + user.firstName + " " + user.lastName + ",<p/>" + message + "</body></html>"
      MailerService.send(EmailMessage(subject, user.email, mail.key, msg, msg, smtp)) match {
        case Success(mail) => MailEvents.MAIL_SENT
        case Failure(t) => {
          println(" Hello!!!!", t.printStackTrace())
          MailEvents.MAIL_SENT_FAILED
        }
      }
    })

  }
}
