package repositories.old.zones

import domain.old.zones.Zone
import org.scalatest.FunSuite
import services.old.setup.SetupService
import services.old.users.UserService
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/01/30.
  */
class ZoneRepositoryTest extends FunSuite {

  val zone = Zone("ZM", "Zambia", "Active", "url")

  test("testGetVoteDownId") {
    val result = Await.result(SetupService.setup, 2 minutes)
    val init = Await.result(SetupService.init, 2 minutes)

//    val fut = UserService.hasUserConfirmedAddress("admin@africahash.com")
//    fut map ( results => {
//      println(" Hellos World ")
//    })

//    val select =  Await.result(UserService.hasUserConfirmedAddress("admin@africahash.com"), 2 minutes)
//     println(" The Result is ", select)
  }


}
