package services.users

import domain.users.User

import java.time.LocalDateTime
import conf.util.HashcodeKeys
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class UserServiceTest extends FunSuite {
  val email = "2leradebe@gmail.com"
  val entity = User("CPUT", email, Some("thulebona"), Some("hadebe"), Some("None"), "passwd", HashcodeKeys.ACTIVE, " ", LocalDateTime.now)
  val service = UserService

  test("Create USER"){
    val result = Await.result(service.saveOrUpdate(entity),2.minutes)
    assert(result.isExhausted)
    println(result)
  }

  test("Get Site User "){
    val result = Await.result(service.getSiteUsers(entity.siteId),2.minutes)
    assert(result.head.siteId equals entity.siteId)
    println("The value of Site User is ",result)
  }

  test("Get User "){
    val result = Await.result(service.getSiteUser(entity.email, entity.siteId),2.minutes)
    assert(result.isDefined)
    println(result)
  }

}

