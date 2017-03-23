package services.util.Impl

import java.util.UUID

import conf.security.AuthUtil
import conf.util.{HashcodeKeys, Util}
import domain.syslog.SystemLogEvents
import domain.users.User
import domain.util.{EmailMessage, Mail, MailEvents, SmtpConfig}
import org.joda.time.DateTime
import services.syslog.SyslogService
import services.users.UserService
import services.util.{MailService, ProcessMailService}

import scala.concurrent.Future
import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/03/12.
  */
class ProcessMailServiceImpl extends ProcessMailService {
  override def sendMail(user: User): Future[String] = {

    val mailer = MailService.getMailer(HashcodeKeys.MAILORG)
    val result = mailer map (mail => {

      val passwd = AuthUtil.generateRandomPassword()
      val updatePerson = user.copy(password = AuthUtil.encode(passwd))
      UserService.saveOrUpdate(updatePerson)
      val subject = "New Login Credentials "
      val message = "Your Login Details are Username: " + updatePerson.email + " And the Password: " + passwd + "" +
        "</p> You can access the Site  Provided to you By the Provider. " +
        "<b>PLEASE REMEMBER TO CHANGE YOUR PASSWORD</b><p/>" +
        "We are Sure your Superiors have told you that Great Powers Come with Great Responsibility"

      sendMail(message, subject, user, mail) map (result => {
        if (result == MailEvents.MAIL_SENT) {
          val successEvent = SystemLogEvents(
            user.siteId,
            Util.md5Hash(UUID.randomUUID().toString),
            MailEvents.MAIL,
            MailEvents.MAIL_SENT, MailEvents.MAIL_SENT, new DateTime())
          SyslogService.save(successEvent)
          MailEvents.MAIL_SENT
        } else {
          val failEvent = SystemLogEvents(
            user.siteId,
            Util.md5Hash(UUID.randomUUID().toString),
            MailEvents.MAIL,
            MailEvents.MAIL_SENT_FAILED, MailEvents.MAIL_SENT_FAILED, new DateTime())
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

        val passwd = AuthUtil.generateRandomPassword()
        val updatePerson = user.copy(password = AuthUtil.encode(passwd))
        UserService.saveOrUpdate(updatePerson)
        val subject = "Your Reset New Login Credentials "

        val message = "Your New Login Details are Username: " + updatePerson.email + " And the Password: " + passwd + "" +
          "</p> You can access the Site  Provided to you By the Provider. " +
          "<b>PLEASE REMEMBER TO CHANGE YOUR PASSWORD</b><p/>" +
          "We are Sure your Superiors have told you that Great Powers Come with Great Responsibility"

        sendMail(message, subject, user, mail) map (result => {
          if (result == MailEvents.MAIL_SENT) {
            val successEvent = SystemLogEvents(
              user.siteId,
              Util.md5Hash(UUID.randomUUID().toString),
              MailEvents.MAIL,
              MailEvents.MAIL_SENT, MailEvents.MAIL_SENT, new DateTime())
            SyslogService.save(successEvent)
            MailEvents.MAIL_SENT
          } else {
            val failEvent = SystemLogEvents(
              user.siteId,
              Util.md5Hash(UUID.randomUUID().toString),
              MailEvents.MAIL,
              MailEvents.MAIL_SENT_FAILED, MailEvents.MAIL_SENT_FAILED, new DateTime())
            SyslogService.save(failEvent)
            MailEvents.MAIL_SENT_FAILED
          }
        })
      })
      result flatMap  { response => response}
  }

  override def sendMail(message: String, subject: String, user: User, mail: Mail): Future[String] = {

    val smtp = SmtpConfig(port = mail.port.toInt, host = mail.host, user = mail.key, password = mail.value)

    val msg = "<html>" +
      "<body>" +
      "<h2><u>The Message Content</u></h2>" +
      "Dear " + user.firstname + " " + user.lastName + ",<p/>" + message + "</body></html>"
    Future {
      MailerService.send(EmailMessage(subject, user.email, mail.key, msg, msg, smtp)) match {
        case Success(mail) => MailEvents.MAIL_SENT
        case Failure(t) => MailEvents.MAIL_SENT_FAILED
      }
    }
  }
}
