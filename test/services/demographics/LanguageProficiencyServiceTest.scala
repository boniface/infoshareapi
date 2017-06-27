package services.demographics

import domain.demographics.LanguageProficiency
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class LanguageProficiencyServiceTest extends FunSuite with BeforeAndAfter {

  val languageProService = LanguageProficiencyService
  var languageProEntity, updateEntity: LanguageProficiency = _

  before{
    languageProEntity = LanguageProficiency(id = "1", name = "Isizulu", state = "Active")
  }

  test("create languageProficiency"){
    val resp = Await.result(languageProService.save(languageProEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get languageProficiency by id"){
    val resp = Await.result(languageProService.getById(languageProEntity.id), 2.minutes)
    assert(resp.head.id == languageProEntity.id)
    assert(resp.head.name == languageProEntity.name)
    assert(resp.head.state == languageProEntity.state)
  }

  test("update languageProficiency"){
    updateEntity = languageProEntity.copy(name = "English")
    val update = Await.result(languageProService.save(updateEntity), 2.minutes)
    val resp = Await.result(languageProService.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != languageProEntity.name)
  }

  test("get all languageProficiency"){
    val result = Await.result(languageProService.save(languageProEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(languageProService.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete languageProficiency"){
    val result = Await.result(languageProService.deleteById(languageProEntity.id), 2.minutes)
    val resp = Await.result(languageProService.getById(languageProEntity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
