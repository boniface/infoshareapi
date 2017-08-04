package services.location

import domain.location.AddressType
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class AddressTypeServiceTest extends FunSuite with BeforeAndAfter {

  val service = AddressTypeService
  var entity, updateEntity: AddressType = _

  before{
    entity = factories.getAddressType
  }

  test("create AddressType"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get AddressType by id"){
    val resp = Await.result(service.getAddressById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update AddressType"){
    updateEntity = entity.copy(name = "work")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getAddressById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all AddressType"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAllAddresses, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete AddressType"){
    val result = Await.result(service.deleteById(entity.id), 2.minutes)
    val resp = Await.result(service.getAddressById(entity.id), 2.minutes)

    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
