package services.location

import domain.location.ContactType
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class ContactTypeServiceTest extends FunSuite with BeforeAndAfter {

  val c_TypeService = ContactTypeService
  var c_Entity, updateEntity: ContactType = _

  before{
    c_Entity = ContactType(id = "1", name = "mobile", state = "Active")
  }

  test("create contact type"){
    val resp = Await.result(c_TypeService.save(c_Entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get contact type by id"){
    val resp = Await.result(c_TypeService.getContactById(c_Entity.id), 2.minutes)
    assert(resp.head.id == c_Entity.id)
    assert(resp.head.name == c_Entity.name)
    assert(resp.head.state == c_Entity.state)
  }

  test("update contact type"){
    updateEntity = c_Entity.copy(name = "work")
    val update = Await.result(c_TypeService.save(updateEntity), 2.minutes)
    val resp = Await.result(c_TypeService.getContactById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name != c_Entity.name)
    assert(resp.head.name == updateEntity.name)
  }
  test("get all contact type"){
    val result = Await.result(c_TypeService.save(c_Entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(c_TypeService.getAllContactType, 2.minutes)

    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete contact type"){
    val result = Await.result(c_TypeService.deleteById(c_Entity.id), 2.minutes)
    val resp = Await.result(c_TypeService.getContactById(c_Entity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
