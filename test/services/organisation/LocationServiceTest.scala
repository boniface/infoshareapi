package services.organisation

import domain.organisation.Location
import org.scalatest.{BeforeAndAfter, FunSuite}
import java.time.{LocalDateTime =>Date}
import scala.concurrent.Await
import scala.concurrent.duration._


class LocationServiceTest extends FunSuite with BeforeAndAfter {

  val service = LocationService
  var entity, updateEntity: Location = _
  var kwargs: Map[String,String] = _

  before{
    entity = Location(id = "1",org = "cput",name = "cape town", locationTypeId="53",code="7580",
      latitude="68",longitude="454",parentId="1",state="Active",date=Date.now())
    kwargs = Map("org"->entity.org,"id"->entity.id)
  }

  test("create location"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get location by id"){
    val resp = Await.result(service.getById(kwargs), 2.minutes)
    assert(resp.head.id == entity.id)
    assert(resp.head.name == entity.name)
    assert(resp.head.state == entity.state)
  }

  test("update location"){
    updateEntity = entity.copy(name="Camelot")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != entity.name)
  }

  test("get all location"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll(kwargs("org")), 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete location"){
    val result = Await.result(service.delete(kwargs), 2.minutes)
    val resp = Await.result(service.getById(kwargs), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
