package services.users

import domain.users.User
import java.time.{LocalDateTime => Date}
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class UserServiceTest extends FunSuite{
  val entity = User("CPUT","test@test.com","First Name","Last Name",None,Some("CODER"),"test123","ACTIVE",Date.now() )
  val updatedEntity = entity.copy(state = "INACTIVE")
  val service = UserService

  test("Create USER"){
    val result = Await.result(service.save(entity),2 minutes)
    assert(result.isExhausted)
  }

  test{"Read  USER "}{
    val result = Await.result(service.getUser("CPUT","test@test.com"),2 minutes)
    assert(result.head.state=="ACTIVE")
  }

  test("Update USER"){
    val update = Await.result(service.save(updatedEntity),2 minutes)
    val result = Await.result(service.getUser(entity.siteId,entity.email),2 minutes)
    assert(result.head.state=="INACTIVE")
  }

  test("get user by email"){
    val result = Await.result(service.getUserByEmail("test@test.com"),2 minutes)
    assert(result.head.email == entity.email)
    assert(result.head.firstName == entity.firstName)
    assert(result.head.lastName == entity.lastName)
  }

  test("get all org users"){
    val resp = Await.result(service.save(entity.copy(email = "test2@test.com")),2 minutes)
    val result = Await.result(service.getUsers(entity.siteId),2 minutes)
    assert(result.head.state=="INACTIVE")
    assert(result.size > 1)
    assert(resp.isExhausted)
  }

}

