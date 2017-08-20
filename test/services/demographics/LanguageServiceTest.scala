package services.demographics

import domain.demographics.Language
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class LanguageServiceTest extends FunSuite with BeforeAndAfter {

  val service = LanguageService
  var entity, updateEntity: Language = _

  before{
    entity = factories.getLanguage
  }

  test("create language"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get language by id"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update language"){
    updateEntity = entity.copy(name = "Isixhosa")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all language"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete language"){
    val result = Await.result(service.deleteById(entity.id), 2.minutes)
    val resp = Await.result(service.getById(entity.id), 2.minutes)

    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
