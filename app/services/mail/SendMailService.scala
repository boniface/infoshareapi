package services.mail

import domain.users.User
import services.mail.Impl.SendMailServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 6/24/17.
  */
trait SendMailService {

  def send(user: User, subject: String, message: String): Future[Int]
}

object SendMailService{

  def apply: SendMailService = new SendMailServiceImpl()
}
