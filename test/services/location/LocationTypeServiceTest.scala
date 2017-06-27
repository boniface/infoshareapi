package services.location

import domain.location.LocationType
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class LocationTypeServiceTest extends FunSuite with BeforeAndAfter {

  val l_TypeService = LocationTypeService
  var l_Entity, updateEntity: LocationType = _

  before{
    l_Entity = LocationType(id = "1", name = "l type", code = "321", state = "Active")
  }

  test("create location type"){
    val resp = Await.result(l_TypeService.save(l_Entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get location type by id"){
    val resp = Await.result(l_TypeService.getLocById(l_Entity.id), 2.minutes)
    assert(resp.head.id == l_Entity.id)
    assert(resp.head.name == l_Entity.name)
    assert(resp.head.state == l_Entity.state)
  }

  test("update location type"){
    updateEntity = l_Entity.copy(name = "my loc type")
    val update = Await.result(l_TypeService.save(updateEntity), 2.minutes)
    val resp = Await.result(l_TypeService.getLocById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name != l_Entity.name)
    assert(resp.head.name == updateEntity.name)
  }
  test("get all location type"){
    val result = Await.result(l_TypeService.save(l_Entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(l_TypeService.getAllLocactions, 2.minutes)

    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete contact type"){
    val result = Await.result(l_TypeService.deleteById(l_Entity.id), 2.minutes)
    val resp = Await.result(l_TypeService.getLocById(l_Entity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
