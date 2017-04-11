package services.util

import domain.person.Person
import domain.util.Mail
import services.util.Impl.ProcessMailServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 2017/03/12.
  */
trait ProcessMailService {

  def sendMail(user: Person): Future[String]

  def resetAccount(user: Person): Future[String]

  def sendMail(message: String, subject: String, user: Person, mail: Mail): Future[String]

}

object ProcessMailService {

  def apply: ProcessMailService = new ProcessMailServiceImpl()
}
