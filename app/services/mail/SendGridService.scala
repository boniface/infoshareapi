package services.mail

import com.sendgrid.{Content, Email}
import services.mail.Impl.SendGridServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 6/24/17.
  */
trait SendGridService {

  def sendMail(from:Email,subject:String,to:Email,content:Content):Future[Int]

}

object SendGridService{
  def apply: SendGridService = new SendGridServiceImpl()
}