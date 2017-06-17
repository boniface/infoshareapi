package services.demographics

import domain.demographics.Race
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class RaceServiceTest extends FunSuite with BeforeAndAfter {

  val raceService = RaceService
  var raceEntity, updateEntity: Race = _

  before{
    raceEntity = Race(id = "1", name = "African", state = "Active")
  }

  test("create race"){
    val resp = Await.result(raceService.save(raceEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get race by id"){
    val resp = Await.result(raceService.getById(raceEntity.id), 2.minutes)
    assert(resp.head.id == raceEntity.id)
    assert(resp.head.name == raceEntity.name)
    assert(resp.head.state == raceEntity.state)
  }

  test("update race"){
    updateEntity = raceEntity.copy(name = "White")
    val update = Await.result(raceService.save(updateEntity), 2.minutes)
    val resp = Await.result(raceService.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != raceEntity.name)
  }

  test("get all race"){
    val result = Await.result(raceService.save(raceEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(raceService.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete race"){
    val result = Await.result(raceService.deleteById(raceEntity.id), 2.minutes)
    val resp = Await.result(raceService.getById(raceEntity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
