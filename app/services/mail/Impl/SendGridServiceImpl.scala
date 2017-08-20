package services.mail.Impl

import com.sendgrid._
import conf.util.HashcodeKeys
import domain.util.Keys
import services.mail.SendGridService
import services.util.KeysService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class SendGridServiceImpl extends SendGridService{
  override def sendMail(from: Email, subject: String, to: Email, content: Content): Future[Int] = {
    val  mail = new Mail(from,subject,to,content)
    for{
      mailKey <- KeysService.getKeyById(HashcodeKeys.MAILKEY)
    } yield {
      val sg = new SendGrid(getKey(mailKey))
      val request  = new Request
      request.setMethod(Method.POST)
      request.setEndpoint("mail/send")
      request.setBody(mail.build)
      val  response = sg.api(request)
      response.getStatusCode
    }
  }

  private def getKey(mailKey: Option[Keys]): String = mailKey match{
    case Some(key) => key.value
    case None => "NONE"
  }
}

