package services.users

import domain.users.User
import java.time.{LocalDateTime => Date}
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class UserServiceTest extends FunSuite{
  val entity = User("CPUT",
    "test@test.com",
    Some("NAME"),
    Some("NAME"),
    "Geek",
    "test123",
    "ACTIVE",
    Date.now())
  val updatedEntity = entity.copy(state = "INACTIVE")
  val service = UserService

  test("Create USER"){
    val result = Await.result(service.saveOrUpdate(entity),2 minutes)
    assert(result.isExhausted)
  }

  test{"Get Site User "}{
    val result = Await.result(service.database.userTimeLineTable.getUsersAccountsOlderThanOneDay("CPUT"),2 minutes)
    println("The value of Site User is ",result)
//    assert(result.getOrElse(User.identity).state=="ACTIVE")
  }

  test{"Get Site Users "}{
    val result = Await.result(service.getSiteUsers("CPUT"),2 minutes)
//    assert(result.head.state=="ACTIVE")
  }



}

