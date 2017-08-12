package services.syslog

import javax.inject.Singleton

import com.outworkers.phantom.dsl.ResultSet
import domain.syslog.SystemLogEvents
import repositories.syslog.SyslogRepository

import scala.concurrent.Future

trait SyslogService extends SyslogRepository {

  def save(systemLogEvents: SystemLogEvents): Future[ResultSet] = {
    database.systemLogEventsTable.save(systemLogEvents)
  }

  def getById(map: Map[String,String]): Future[Option[SystemLogEvents]] = {
    database.systemLogEventsTable.getById(map)
  }

  def getAll(siteId: String): Future[Seq[SystemLogEvents]] = {
    database.systemLogEventsTable.getAll(siteId)
  }

}

@Singleton
object SyslogService extends SyslogService with SyslogRepository
