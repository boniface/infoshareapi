package services.syslog

import domain.syslog.SystemLogEvents
import org.joda.time.DateTime
import org.scalatest.{BeforeAndAfter, FunSuite}


class SyslogServiceTest extends FunSuite with BeforeAndAfter {

  val syslogService = SyslogService
  var syslogEntity, updateEntity: SystemLogEvents = _

  before{
    syslogEntity = SystemLogEvents(org = "BO", id = "1", eventName = "test",eventType = "test",
                                    message = "my test",date = new DateTime())
  }

  //todo finish test
}
