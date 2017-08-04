package services.mail

import domain.users.User
import services.mail.Impl.MailDeliveryServiceImpl

import scala.concurrent.Future


trait MailDeliveryService {
  def sendMail(user:User):Future[String]
  def resetAccount(user:User):Future[String]
  def sendMail(message: String, subject: String, user: User): Future[String]
}

object MailDeliveryService{
  def apply: MailDeliveryService = new MailDeliveryServiceImpl()
}
