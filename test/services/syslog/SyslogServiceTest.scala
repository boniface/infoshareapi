package services.syslog

import util.factories
import domain.syslog.SystemLogEvents
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class SyslogServiceTest extends FunSuite with BeforeAndAfter {

  val service = SyslogService
  var entity, updateEntity: SystemLogEvents = _
  var kwargs: Map[String,String] =  _

  before{
    entity = factories.getSystemLog
    kwargs = Map("siteId"->entity.siteId, "id"->entity.id)
  }

  test("create SystemLogEvents") {
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get SystemLogEvents by id") {
    val resp = Await.result(service.getById(kwargs), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update SystemLogEvents") {
    updateEntity = entity.copy(message = "login error", eventName = "invalid token", eventType = "save user")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all SystemLogEvents") {
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll(kwargs("siteId")), 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }
}
