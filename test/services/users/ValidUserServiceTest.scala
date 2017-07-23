package services.users

import java.time.LocalDateTime

import conf.util.Events
import domain.users.ValidUser
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import util.factories

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

    val vuser1 = factories.getValidUser
    val vuser2 = vuser1.copy(action = Events.LOGGEDIN)
    val vuser3 = vuser1.copy(action = Events.LOGGEOUT)
    val vuser4 = vuser1.copy(userId = "2", action = Events.VALIDATED)

    service.save(vuser1)
    Thread.sleep(2000)
    service.save(vuser2)
    Thread.sleep(2000)
    service.save(vuser3)
    Thread.sleep(2000)
    service.save(vuser4)

  }

  test("testIsUserValid") {
    val result = Await.result(service.isUserValid("CPUT","1"), 2 minutes)
    print(" THE RESPONSE IS ", result)

  }

  test("testGetValidUserEvents") {

    val result = Await.result(service.getValidUserEvents("CPUT","1"), 2 minutes)
    print(" THE RESPONSE IS FOR VALID USESRS ", result)

  }

  test("testGetValidUsers") {
    val result = Await.result(service.getValidUsers, 2 minutes)
    print(" THE RESPONSE IS ", result)

  }

}
