package services.util

import com.outworkers.phantom.dsl.ResultSet
import domain.util.Mail
import repositories.util.MailRepository

import scala.concurrent.Future
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/01/29.
  */
trait MailService extends MailRepository {

  def save(mail: Mail): Future[ResultSet] = {

    database.mailTable.save(mail)

  }

  def getMailSettingById(siteId:String, id: String): Future[Option[Mail]] = {

    database.mailTable.getMailSettingById(siteId,id)

  }

  def getAllMailSettings(siteId:String): Future[Seq[Mail]] = {

    database.mailTable.getAllMailSettings(siteId)

  }

  def getMailer(siteId: String):Future[Mail] = getAllMailSettings(siteId) map (
    result => {
      result.toVector( new Random().nextInt(result.size))
    })

}



object MailService extends MailService with MailRepository
