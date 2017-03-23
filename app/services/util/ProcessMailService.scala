package services.util

import domain.users.User
import domain.util.Mail
import services.util.Impl.ProcessMailServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/03/12.
  */
trait ProcessMailService {
  def sendMail(user:User):Future[String]
  def resetAccount(user:User):Future[String]
  def sendMail(message: String, subject: String, user: User, mail: Mail): Future[String]
}

object ProcessMailService{
  def apply: ProcessMailService = new ProcessMailServiceImpl()
}
