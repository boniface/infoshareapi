package services.organisation

import domain.organisation.Location
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class LocationServiceTest extends FunSuite with BeforeAndAfter {

  val service = LocationService
  var entity, updateEntity: Location = _
  var kwargs: Map[String,String] = _

  before{
    entity = factories.getLocation
    kwargs = Map("org"->entity.org,"id"->entity.id)
  }

  test("create location"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get location by id"){
    val resp = Await.result(service.getById(kwargs), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update location"){
    updateEntity = entity.copy(name="Camelot")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all location"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll(kwargs("org")), 2.minutes)

    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete location"){
    val result = Await.result(service.delete(kwargs), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)

    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
