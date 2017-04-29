package services.person

import conf.enums.RolesID
import domain.person.UserRole
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._

class UserRoleServiceTest extends FunSuite with BeforeAndAfter {
  val userRoleService = UserRoleService
  var userRoleEntity: UserRole = _

  before{
    userRoleEntity = UserRole("test@test.com",RolesID.ADMIN,"ACTIVE")
  }

  test("Create USER_ROLE"){
    val result = Await.result(userRoleService.save(userRoleEntity),2.minutes)
    assert(result.isExhausted)
  }

  test{"GET  USER_ROLES BY_USER_Id "}{
    userRoleService.save(userRoleEntity.copy(roleId = RolesID.ROLE_PUBLISHER))

    val result = Await.result(userRoleService.getRolesByUserId(userRoleEntity.userId),2.minutes)
    assert(result.head.roleId==RolesID.ADMIN)
    assert(result.last.roleId==RolesID.ROLE_PUBLISHER)
  }

  test{"GET  USER_ROLE "}{
    val result = Await.result(userRoleService.getUserRole( Map("userId"->userRoleEntity.userId, "roleId"->RolesID.ADMIN )),2 minutes)
    assert(result.head.roleId==RolesID.ADMIN)
    assert(result.head.userId==userRoleEntity.userId)
  }

  test("Upadte USER_ROLE"){
    userRoleEntity.copy(roleId = RolesID.ROLE_PUBLISHER)
    Await.result(userRoleService.save(userRoleEntity),2.minutes)
    val result = Await.result(userRoleService.getUserRole(Map("userId"->userRoleEntity.userId,"roleId"-> RolesID.ROLE_PUBLISHER)),2.minutes)
    assert(result.head.roleId==RolesID.ROLE_PUBLISHER)
  }

  test("Delete USER_ROLE") {
    val result = Await.result(userRoleService.deleteRole(Map("userId" -> userRoleEntity.userId, "roleId" -> RolesID.ROLE_PUBLISHER)), 2.minutes)
    val result2 = Await.result(userRoleService.getUserRole(Map("userId"->userRoleEntity.userId,"roleId"-> RolesID.ROLE_PUBLISHER)),2.minutes)
    assert(result2.isEmpty)
    assert(result.isExhausted)
  }
}

