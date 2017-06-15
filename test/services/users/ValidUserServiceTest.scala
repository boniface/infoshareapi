package services.users

import java.time.LocalDateTime

import conf.util.Events
import domain.users.ValidUser
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/06/11.
  */
class ValidUserServiceTest extends FunSuite with BeforeAndAfterEach {

  val service = ValidUserService


  override def beforeEach() {


  }

  override def afterEach() {



  }

  test("testSave") {
    val vuser1 = ValidUser("1",LocalDateTime.now(),Events.VALIDATED)
    val vuser2 = ValidUser("1",LocalDateTime.now(),Events.LOGGEDIN)
    val vuser3 = ValidUser("1",LocalDateTime.now(),Events.LOGGEOUT)
    val vuser4 = ValidUser("2",LocalDateTime.now(),Events.VALIDATED)

    service.save(vuser1)
    Thread.sleep(2000)
    service.save(vuser2)
    Thread.sleep(2000)
    service.save(vuser3)
    Thread.sleep(2000)
    service.save(vuser4)

  }

  test("testIsUserValid") {
    val result = Await.result(service.isUserValid("1"), 2 minutes)
    print(" THE RESPONSE IS ", result)

  }

  test("testGetValidUserEvents") {

    val result = Await.result(service.getValidUserEvents("1"), 2 minutes)
    print(" THE RESPONSE IS FOR VALID USESRS ", result)

  }

  test("testGetValidUsers") {
    val result = Await.result(service.getValidUsers, 2 minutes)
    print(" THE RESPONSE IS ", result)

  }

}
