package services.users

import org.scalatest.{BeforeAndAfterEach, FunSuite}
import services.security.LoginService

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/06/29.
  */
class LoginServiceTest extends FunSuite with BeforeAndAfterEach {
  test("testIsUserValid") {
    val result = Await.result(UserService.getSiteUsers("HASHCODE"), 2 minutes)
    print(" THE RESPONSE IS ", result)

  }

}
