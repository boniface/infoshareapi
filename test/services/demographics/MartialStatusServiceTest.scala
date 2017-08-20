package services.demographics

import conf.util.MaritalStatusList
import domain.demographics.MaritalStatus
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class MartialStatusServiceTest extends FunSuite with BeforeAndAfter {

  val service = MaritalStatusService
  var entity, updateEntity: MaritalStatus = _

  before{
    entity = factories.getMaritalStatus
  }

  test("create martial status"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get martial status by id"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update martial status"){
    updateEntity = entity.copy(name = MaritalStatusList.DIVORCED)
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all martial status"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete martial status"){
    val result = Await.result(service.delete(entity.id), 2.minutes)
    val resp = Await.result(service.getById(entity.id), 2.minutes)

    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
