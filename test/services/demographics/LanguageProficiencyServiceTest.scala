package services.demographics

import domain.demographics.LanguageProficiency
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class LanguageProficiencyServiceTest extends FunSuite with BeforeAndAfter {

  val service = LanguageProficiencyService
  var entity, updateEntity: LanguageProficiency = _

  before{
    entity = factories.getLanguageProficiency
  }

  test("create languageProficiency"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get languageProficiency by id"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update languageProficiency"){
    updateEntity = entity.copy(name = "English")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all languageProficiency"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete languageProficiency"){
    val result = Await.result(service.deleteById(entity.id), 2.minutes)
    val resp = Await.result(service.getById(entity.id), 2.minutes)

    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
