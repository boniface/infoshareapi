package setup

import org.scalatest.FunSuite
import services.setup.SetupService

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/04/21.
  */
class DatabaseSetupTest extends FunSuite{

  test("Create Tables in Cassandra"){
//    val createTable = Await.result(SetupService.setup, 2.minutes)
//    val initialiseDAta = Await.result(SetupService.init, 2.minutes)
//    assert(createTable.isExhausted)
//    assert(initialiseDAta)
  }

  test("drop all Tables in cassandra"){
//    val cleanup = Await.result(SetupService.cleanup, 2.minutes)
//    assert(cleanup.size == 1)
  }

}
