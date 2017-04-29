package services.person

import java.util.Date

import domain.person.{User, UserLanguage}
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class UserLanguageServiceTest extends FunSuite with BeforeAndAfter{

  val userLangService = UserLanguageService
  var userLangEntity, updateEntity: UserLanguage = _


  before{
    userLangEntity = UserLanguage("test@test.com", "1", "1", "eng", "eng", "zulu",new Date(),"Active")
  }

  test("Create USER_LANG"){
    val resp = Await.result(userLangService.save(userLangEntity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_Address"){
    val resp = Await.result(userLangService.getUserLangById(Map("userId"-> userLangEntity.userId,"id"-> userLangEntity.id)),2.minutes)

    assert(resp.head.id == userLangEntity.id)
    assert(resp.head.userId  == userLangEntity.userId)
    assert(resp.head.languageId  == userLangEntity.languageId)
    assert(resp.head.writing == userLangEntity.writing)
    assert(resp.head.reading  == userLangEntity.reading)
    assert(resp.head.speaking  == userLangEntity.speaking)
    assert(resp.head.state  == userLangEntity.state)

  }

  test("UPDATE USER_LANG"){
    updateEntity = userLangEntity.copy(speaking = "Isizulu",date = new Date())
    Await.result(userLangService.save(updateEntity), 2.minutes)
    val resp = Await.result(userLangService.getUserLangById(Map("userId"-> userLangEntity.userId,"id"-> userLangEntity.id)),2.minutes)

    assert(resp.head.id == userLangEntity.id)
    assert(resp.head.userId  == userLangEntity.userId)
    assert(resp.head.languageId  == userLangEntity.languageId)
    assert(resp.head.writing == userLangEntity.writing)
    assert(resp.head.reading  == userLangEntity.reading)
    assert(resp.head.speaking  == updateEntity.speaking)
    assert(resp.head.date  == updateEntity.date)
    assert(resp.head.state  == userLangEntity.state)

  }

  test("GET ALL USER_ADDRESS"){
    val secondAddr = Await.result(userLangService.save(userLangEntity.copy(id = "9",writing = "Xhosa")), 2.minutes)
    val resp = Await.result(userLangService.getAllUserLang(userLangEntity.userId),2.minutes)
    assert(resp.size > 1)
    assert(secondAddr.isExhausted)
  }
}

