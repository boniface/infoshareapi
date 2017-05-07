package services.location

import domain.location.AddressType
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class AddressTypeServiceTest extends FunSuite with BeforeAndAfter {

  val addrTypeService = AddressTypeService
  var addrEntity, updateEntity: AddressType = _

  before{
    addrEntity = AddressType(id = "1", name = "home", state = "Active")
  }

  test("create AddressType"){
    val resp = Await.result(addrTypeService.save(addrEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get AddressType by id"){
    val resp = Await.result(addrTypeService.getAddressById(addrEntity.id), 2.minutes)
    assert(resp.head.id == addrEntity.id)
    assert(resp.head.name == addrEntity.name)
    assert(resp.head.state == addrEntity.state)
  }

  test("update AddressType"){
    updateEntity = addrEntity.copy(name = "work")
    val update = Await.result(addrTypeService.save(updateEntity), 2.minutes)
    val resp = Await.result(addrTypeService.getAddressById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name != addrEntity.name)
    assert(resp.head.name == updateEntity.name)
  }
  test("get all AddressType"){
    val result = Await.result(addrTypeService.save(addrEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(addrTypeService.getAllAddresses, 2.minutes)

    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete AddressType"){
    val result = Await.result(addrTypeService.deleteById(addrEntity.id), 2.minutes)
    val resp = Await.result(addrTypeService.getAddressById(addrEntity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
