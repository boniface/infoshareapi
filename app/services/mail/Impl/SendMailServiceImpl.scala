package services.mail.Impl

import com.sendgrid.{Content, Email}
import domain.users.User
import services.mail.{SendGridService, SendMailService}

import scala.concurrent.Future


class SendMailServiceImpl extends SendMailService{
  override def send(user: User, subject:String, message: String): Future[Int] = {
    val from = new Email("do_not_reply@hashcode.zm")
    val to = new Email(user.email)
    val content = new Content("text/html",message)
    SendGridService.apply.sendMail(from,subject,to,content)
  }
}
