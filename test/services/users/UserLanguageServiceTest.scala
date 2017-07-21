package services.users

import java.time.LocalDateTime

import domain.users.{User, UserLanguage}
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class UserLanguageServiceTest extends FunSuite with BeforeAndAfter{

  val service = UserLanguageService
  var entity, updateEntity: UserLanguage = _
  var kwargs: Map[String,String] = _


  before{
    entity = factories.getUserLanguage
    kwargs = Map("emailId"-> entity.emailId,"id"-> entity.id)
  }

  test("Create USER_LANG"){
    val resp = Await.result(service.save(entity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_LANGS"){
    val resp = Await.result(service.getUserLangById(kwargs),2.minutes)
    assert(resp.get equals entity)
  }

  test("UPDATE USER_LANG"){
    updateEntity = entity.copy(speaking = "Isizulu",date = LocalDateTime.now())
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getUserLangById(kwargs),2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("GET ALL USER_LANGS"){
    val results = Await.result(service.save(entity.copy(id = "9",writing = "Xhosa")), 2.minutes)
    val resp = Await.result(service.getAllUserLang(kwargs("emailId")),2.minutes)

    assert(resp.nonEmpty)
    assert(results.isExhausted)
  }
}

