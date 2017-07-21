package services.demographics

import conf.util.RolesID
import domain.demographics.Role
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class RolesServiceTest extends FunSuite with BeforeAndAfter {

  val service = RoleService
  var entity, updateEntity: Role = _

  before{
    entity = factories.getDemoRole
  }

  test("create role"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get role by id"){
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.get equals entity)
  }

  test("update role"){
    updateEntity = entity.copy(name = RolesID.ADMIN)
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("get all roles"){
    val result = Await.result(service.save(entity.copy(id = "2")), 2.minutes)
    val resp = Await.result(service.getAll, 2.minutes)
    assert(resp.nonEmpty)
    assert(result.isExhausted)
  }

  test("delete role"){
    val result = Await.result(service.delete(entity.id), 2.minutes)
    val resp = Await.result(service.getById(entity.id), 2.minutes)
    assert(resp.isEmpty)
    assert(result.isExhausted)
  }

}
