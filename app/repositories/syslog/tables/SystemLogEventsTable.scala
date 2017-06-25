package repositories.syslog.tables

import java.time.{LocalDateTime => Date}
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.syslog.SystemLogEvents

import scala.concurrent.Future


abstract class SystemLogEventsTable extends Table[SystemLogEventsTable, SystemLogEvents] {

  object org extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object eventName extends StringColumn

  object eventType extends StringColumn

  object message extends StringColumn

  object date extends Col[Date]

}

abstract class SystemLogEventsTableImpl extends SystemLogEventsTable with RootConnector {

  override lazy val tableName = "systemLogEvents"

  def save(systemLogEvents: SystemLogEvents): Future[ResultSet] = {
    insert
      .value(_.org, systemLogEvents.siteId)
      .value(_.id, systemLogEvents.id)
      .value(_.eventName, systemLogEvents.eventName)
      .value(_.eventType, systemLogEvents.eventType)
      .value(_.message, systemLogEvents.message)
      .value(_.date, systemLogEvents.date)
      .ttl(5000)
      .future()

  }

  def getById(map: Map[String, String]): Future[Option[SystemLogEvents]] = {
    select.where(_.org eqs map("org")).and(_.id eqs map("id")).one()
  }

  def getAll(org: String): Future[Seq[SystemLogEvents]] = {
    select.where(_.org eqs org).fetchEnumerator() run Iteratee.collect()
  }

}
