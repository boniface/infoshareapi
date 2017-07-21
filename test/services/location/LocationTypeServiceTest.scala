package services.location

import domain.location.LocationType
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class LocationTypeServiceTest extends FunSuite with BeforeAndAfter {

  val service = LocationTypeService
  var entity, updateEntity: LocationType = _

  before{
    entity = factories.getLocationType
  }

  test("create location type"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get location type by id"){
    val resp = Await.result(service.getLocById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update location type"){
    updateEntity = entity.copy(name = "my loc type")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getLocById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all location type"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAllLocactions, 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete contact type"){
    val result = Await.result(service.deleteById(entity.id), 2.minutes)
    val resp = Await.result(service.getLocById(entity.id), 2.minutes)

    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
