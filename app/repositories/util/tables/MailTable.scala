package repositories.util.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.util.Mail

import scala.concurrent.Future

/**
  * Created by kuminga on 2016/08/29.
  */


abstract class MailTable extends Table[MailTable, Mail] {

  object siteId extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object key extends StringColumn

  object value extends StringColumn

  object host extends StringColumn

  object port extends StringColumn

  object state extends StringColumn

  object date extends DateColumn


}

abstract class  MailTableImpl extends MailTable with RootConnector {

  override lazy val tableName = "mailsettings"

  def save(mail: Mail): Future[ResultSet] = {
    insert
      .value(_.siteId, mail.siteId)
      .value(_.id, mail.id)
      .value(_.key, mail.key)
      .value(_.value, mail.value)
      .value(_.host, mail.host)
      .value(_.port, mail.port)
      .value(_.state, mail.state)
      .value(_.date, mail.date)
      .future()
  }

  def getMailSettingById(siteId:String, id: String): Future[Option[Mail]] = {
    select.where(_.siteId eqs siteId).and(_.id eqs id).one()
  }

  def getAllMailSettings(siteId:String): Future[Seq[Mail]] = {
    select.where(_.siteId eqs siteId).fetchEnumerator() run Iteratee.collect()
  }
}


