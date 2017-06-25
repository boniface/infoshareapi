package services.mail

import domain.users.User
import services.mail.Impl.MailDeliveryServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 6/24/17.
  */
trait MailDeliveryService {
  def sendMail(user:User):Future[String]
  def resetAccount(user:User):Future[String]
  def sendMail(message: String, subject: String, user: User): Future[String]
}

object MailDeliveryService{
  def apply: MailDeliveryService = new MailDeliveryServiceImpl()
}
