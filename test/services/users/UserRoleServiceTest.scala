package services.users

import domain.security.RolesID
import domain.users.UserRole
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._

class UserRoleServiceTest extends FunSuite with BeforeAndAfter {
  val userRoleService = UserRoleService
  var userRoleEntity, updateEntity: UserRole = _

  before{
//    userRoleEntity = UserRole(emailId="test@test.com",roleId = "1", state="ACTIVE")
  }

  test("Create USER_ROLE"){
    val result = Await.result(userRoleService.save(userRoleEntity),2.minutes)
    assert(result.isExhausted)
  }

  test{"GET  USER_ROLES BY_USER_Id "}{
    updateEntity = userRoleEntity.copy(roleId = RolesID.PUBLISHER)
    userRoleService.save(updateEntity)
//
//    val result = Await.result(userRoleService.getById( Map("emailId"->updateEntity.emailId, "roleId"->updateEntity.roleId)),2.minutes)
//    assert(result.head.roleId !=RolesID.ADMIN)
//    assert(result.last.roleId==RolesID.PUBLISHER)
  }


  test("Update USER_ROLE"){
//    updateEntity = userRoleEntity.copy(state = "INACTIVE")
//    val update = Await.result(userRoleService.save(updateEntity),2.minutes)
//    assert(update.isExhausted)
//
//    val result = Await.result(userRoleService.getById(Map("emailId"->updateEntity.emailId,"roleId"-> updateEntity.roleId)),2.minutes)
//    assert(result.head.state==updateEntity.state)
//    assert(result.head.state !=userRoleEntity.state)
  }

  test("get all USER_ROLE"){
//    updateEntity = userRoleEntity.copy(roleId = "2")
//    val update = Await.result(userRoleService.save(updateEntity),2.minutes)
//    val result = Await.result(userRoleService.getAll(updateEntity.emailId),2.minutes)
//    assert(result.size > 1)
//    assert(update.isExhausted)

  }

  test("Delete USER_ROLE") {
//    val result = Await.result(userRoleService.delete(Map("emailId" -> userRoleEntity.emailId, "roleId" -> RolesID.PUBLISHER)), 2.minutes)
//    val result2 = Await.result(userRoleService.getById(Map("emailId"->userRoleEntity.emailId,"roleId"-> RolesID.PUBLISHER)),2.minutes)
//    assert(result2.isEmpty)
//    assert(result.isExhausted)
  }

}

