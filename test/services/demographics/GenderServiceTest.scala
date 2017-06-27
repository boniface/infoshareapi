package services.demographics

import domain.demographics.Gender
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class GenderServiceTest extends FunSuite with BeforeAndAfter {

  val genderService = GenderService
  var genderEntity, updateEntity: Gender = _

  before{
    genderEntity = Gender(id = "1", name = "Male", state = "Active")
  }

  test("create gender"){
    val resp = Await.result(genderService.save(genderEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get gender by id"){
    val resp = Await.result(genderService.getById(genderEntity.id), 2.minutes)
    assert(resp.head.id == genderEntity.id)
    assert(resp.head.name == genderEntity.name)
    assert(resp.head.state == genderEntity.state)
  }

  test("update gender"){
    updateEntity = genderEntity.copy(name = "FEMALE")
    val update = Await.result(genderService.save(updateEntity), 2.minutes)
    val resp = Await.result(genderService.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != genderEntity.name)
  }

  test("get all gender"){
    val result = Await.result(genderService.save(genderEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(genderService.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete gender"){
    val result = Await.result(genderService.deleteById(genderEntity.id), 2.minutes)
    val resp = Await.result(genderService.getById(genderEntity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
