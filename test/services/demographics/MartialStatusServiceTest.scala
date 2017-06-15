package services.demographics

import conf.util.MaritalStatusList
import domain.demographics.MaritalStatus
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class MartialStatusServiceTest extends FunSuite with BeforeAndAfter {

  val service = MaritalStatusService
  var entity, updateEntity: MaritalStatus = _

  before{
    entity = MaritalStatus(id = "1", name = MaritalStatusList.MARRIED, state = "Active")
  }

  test("create martial status"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get martial status by id"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.head.id == entity.id)
    assert(resp.head.name == entity.name)
    assert(resp.head.state == entity.state)
  }

  test("update martial status"){
    updateEntity = entity.copy(name = MaritalStatusList.DIVORCED)
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(entity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != entity.name)
  }

  test("get all martial status"){
    val result = Await.result(service.save(updateEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete martial status"){
    val result = Await.result(service.delete(entity.id), 2.minutes)
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
