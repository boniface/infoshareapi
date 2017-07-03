package services.organisation

import java.time.LocalDateTime

import domain.organisation.Organisation
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class OrganisationServiceTest extends FunSuite with BeforeAndAfter {

  val service = OrganisationService
  var entity, updateEntity: Organisation = _

  before{
    entity = Organisation(id = "1",name = "cput",details = Map(),adminAttached = "admin",date = LocalDateTime.now(),state = "ACTIVE")
  }

  test("create Organisation"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get Organisation by id"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.head.id == entity.id)
    assert(resp.head.name == entity.name)
    assert(resp.head.state == entity.state)
  }

  test("update Organisation"){
    updateEntity = entity.copy(name="dut")
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != entity.name)
  }

  test("get all Organisation"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }


}
