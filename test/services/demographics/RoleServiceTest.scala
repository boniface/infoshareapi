package services.demographics

import domain.demographics.Role
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class RoleServiceTest extends FunSuite with BeforeAndAfter {

  val roleService = RoleService
  var roleEntity, updateEntity: Role = _

  before{
    roleEntity = Role(id = "1", name = "my role", description = "role", state = "Active")
  }

  test("create role"){
    val resp = Await.result(roleService.save(roleEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get role by id"){
    val resp = Await.result(roleService.getById(roleEntity.id), 2.minutes)
    assert(resp.head.id == roleEntity.id)
    assert(resp.head.name == roleEntity.name)
    assert(resp.head.state == roleEntity.state)
  }

  test("update role"){
    updateEntity = roleEntity.copy(name = "your role")
    val update = Await.result(roleService.save(updateEntity), 2.minutes)
    val resp = Await.result(roleService.getById(roleEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.name == updateEntity.name)
    assert(resp.head.name != roleEntity.name)
  }

  test("get all roles"){
    val result = Await.result(roleService.save(updateEntity.copy(id = "2")), 2.minutes)
    val resp = Await.result(roleService.getAll, 2.minutes)
    assert(result.isExhausted)
    assert(resp.size > 1)
  }

  test("delete role"){
    val result = Await.result(roleService.delete(roleEntity.id), 2.minutes)
    val resp = Await.result(roleService.getById(roleEntity.id), 2.minutes)
    assert(result.isExhausted)
    assert(resp.isEmpty)
  }

}
