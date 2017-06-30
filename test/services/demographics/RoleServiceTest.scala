package services.demographics

import domain.security.Roles
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class RoleServiceTest extends FunSuite with BeforeAndAfter {

  val roleService = RoleService
  var roleEntity, updateEntity: Roles = _

  before{
    roleEntity = Roles(id = "1", rolename = "my role")
  }

  test("create role"){
    val resp = Await.result(roleService.save(roleEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("get role by id"){
    val resp = Await.result(roleService.getById(roleEntity.id), 2.minutes)
    assert(resp.head.id == roleEntity.id)
    assert(resp.head.rolename == roleEntity.rolename)

  }

  test("update role"){
    updateEntity = roleEntity.copy(rolename = "your role")
    val update = Await.result(roleService.save(updateEntity), 2.minutes)
    val resp = Await.result(roleService.getById(updateEntity.id), 2.minutes)

    assert(update.isExhausted)
    assert(resp.head.rolename == updateEntity.rolename)
    assert(resp.head.rolename != roleEntity.rolename)
  }

  test("get all roles"){
    val result = Await.result(roleService.save(roleEntity.copy(id = "2")), 2.minutes)
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
