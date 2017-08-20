package services.location

import domain.location.ContactType
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class ContactTypeServiceTest extends FunSuite with BeforeAndAfter {

  val service = ContactTypeService
  var entity, updateEntity: ContactType = _

  before{
    entity = factories.getContactType
  }

  test("create contact type"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get contact type by id"){
    val resp = Await.result(service.getContactById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update contact type"){
    updateEntity = entity.copy(name = "Phone")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getContactById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all contact type"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAllContactType, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete contact type"){
    val result = Await.result(service.deleteById(entity.id), 2.minutes)
    val resp = Await.result(service.getContactById(entity.id), 2.minutes)

    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
