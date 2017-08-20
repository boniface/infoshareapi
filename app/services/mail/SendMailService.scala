package services.mail

import domain.users.User
import services.mail.Impl.SendMailServiceImpl

import scala.concurrent.Future



trait SendMailService {
  def send(user: User, subject: String, message: String): Future[Int]
}

object SendMailService {
  def apply: SendMailService = new SendMailServiceImpl()
}
