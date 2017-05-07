package repositories.syslog.tables

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import domain.syslog.SystemLogEvents

import scala.concurrent.Future


/**
  * Created by Quest on 2016/10/25.
  */
class SystemLogEventsTable extends CassandraTable[SystemLogEventsTable, SystemLogEvents] {

  object siteId extends StringColumn(this) with PartitionKey

  object id extends StringColumn(this) with PrimaryKey

  object eventName extends StringColumn(this)

  object eventType extends StringColumn(this)

  object message extends StringColumn(this)

  object date extends DateTimeColumn(this)

}

abstract class SystemLogEventsTableImpl extends SystemLogEventsTable with RootConnector {

  override lazy val tableName = "systemLogEvents"

  def save(systemLogEvents: SystemLogEvents): Future[ResultSet] = {
    insert
      .value(_.siteId, systemLogEvents.org)
      .value(_.id, systemLogEvents.id)
      .value(_.eventName, systemLogEvents.eventName)
      .value(_.eventType, systemLogEvents.eventType)
      .value(_.message, systemLogEvents.message)
      .value(_.date, systemLogEvents.date)
      .ttl(5000)
      .future()

  }

  def getEventById(siteId: String, id: String): Future[Option[SystemLogEvents]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.id eqs id)
      .one()
  }

  def getSiteLogs(siteId: String): Future[Seq[SystemLogEvents]] = {
    select
      .where(_.siteId eqs siteId)
      .fetchEnumerator() run Iteratee.collect()
  }

}
