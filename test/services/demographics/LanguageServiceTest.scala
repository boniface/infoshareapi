package services.demographics

import domain.demographics.Language
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class LanguageServiceTest extends FunSuite with BeforeAndAfter {

  val languageService = LanguageService
  var languageEntity, updateEntity: Language = _

  before{
    languageEntity = Language(id = "1", name = "Isizulu", state = "Active")
  }

  test("create language"){
    val resp = Await.result(languageService.save(languageEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get language by id"){
    val resp = Await.result(languageService.getById(languageEntity.id), 2.minutes)
    assert(resp.head.id == languageEntity.id)
    assert(resp.head.name == languageEntity.name)
    assert(resp.head.state == languageEntity.state)
  }

  test("update language"){
    updateEntity = languageEntity.copy(name = "Isixhosa")
    val update = Await.result(languageService.save(updateEntity), 2.minutes)
    val resp = Await.result(languageService.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != languageEntity.name)
  }

  test("get all language"){
    val result = Await.result(languageService.save(languageEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(languageService.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete language"){
    val result = Await.result(languageService.deleteById(languageEntity.id), 2.minutes)
    val resp = Await.result(languageService.getById(languageEntity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
