package services.users

import domain.users.UserRole
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._

class UserRoleServiceTest extends FunSuite with BeforeAndAfter {
  val service = UserRoleService
  var entity, updateEntity: UserRole = _

  before{
    entity = factories.getUserRole
  }

  test("Create USER_ROLE"){
    val result = Await.result(service.save(entity),2.minutes)
    assert(result.isExhausted)
  }

  test("GET  USER_ROLES BY_USER_Id "){
    val result = Await.result(service.getUserRole(factories.getUser),2.minutes)
    assert(result equals entity)
  }


  test("get all USER_ROLE"){
    updateEntity = entity.copy(roleId = "2")
    val update = Await.result(service.save(updateEntity),2.minutes)
    val result = Await.result(service.getUserRoles(factories.getUser),2.minutes)
    assert(result.nonEmpty)
    assert(update.isExhausted)

  }

  test("Delete USER_ROLE") {
    val result = Await.result(service.deleteUserRoles(factories.getUser), 2.minutes)
    val resp = Await.result(service.getUserRole(factories.getUser),2.minutes)

    assert(resp != entity)
    assert(result.isExhausted)
  }

}

