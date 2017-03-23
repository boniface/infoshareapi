package repositories.zones.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.sites.Administrators

import scala.concurrent.Future

/**
  * Created by Quest on 2016/10/24.
  */
class AdministratorsTable extends CassandraTable[AdministratorsTable, Administrators] {

  object siteId extends StringColumn(this) with PartitionKey

  object emailId extends StringColumn(this) with PrimaryKey

}

abstract class AdministratorsTableImpl extends AdministratorsTable with RootConnector {

  override lazy val tableName = "administrators"


  def saveAdministrator(administrators: Administrators): Future[ResultSet] = {
    insert
      .value(_.siteId, administrators.siteId)
      .value(_.emailId, administrators.emailId)
      .future()
  }

  def getSiteAdministrator(siteId: String, emailId: String): Future[Option[Administrators]]  = {
    select.where(_.siteId eqs siteId).and(_.emailId eqs emailId).one()
  }

  def getSiteAdministrators(siteId: String): Future[Seq[Administrators]] = {
    select.where(_.siteId eqs siteId)fetchEnumerator() run Iteratee.collect()
  }

}
