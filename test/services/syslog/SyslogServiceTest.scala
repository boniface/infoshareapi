package services.syslog

import domain.syslog.SystemLogEvents
import java.time.{LocalDateTime => Date}

import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class SyslogServiceTest extends FunSuite with BeforeAndAfter {

  val service = SyslogService
  var entity, updateEntity: SystemLogEvents = _
  var kwargs: Map[String,String] =  _

  before{
    entity = SystemLogEvents(org = "BO", id = "1", eventName = "test",eventType = "test",
                                    message = "my test",date = Date.now())
    kwargs = Map("org"->entity.org, "id"->entity.id)
  }

  test("create SystemLogEvents") {
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get SystemLogEvents by id") {
    val resp = Await.result(service.getById(kwargs), 2.minutes)
    assert(resp.head.id == entity.id)
    assert(resp.head.eventName == entity.eventName)
    assert(resp.head.eventType == entity.eventType)
    assert(resp.head.message == entity.message)
  }

  test("update SystemLogEvents") {
    updateEntity = entity.copy(message = "you have null point exception", eventName = "null", eventType = "save user")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.eventName == updateEntity.eventName)
    assert(resp.head.eventType == updateEntity.eventType)
    assert(resp.head.message == updateEntity.message)

    assert(resp.head.eventName != entity.eventName)
    assert(resp.head.eventType != entity.eventType)
    assert(resp.head.message != entity.message)
  }

  test("get all SystemLogEvents") {
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll(kwargs("org")), 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }
}
