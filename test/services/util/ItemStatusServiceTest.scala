package services.util

import domain.util.ItemStatus
import java.time.{LocalDateTime => Date}
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/04/22.
  */
class ItemStatusServiceTest extends FunSuite{


  val entity = ItemStatus("1",Date.now(),"ACTIVE","USER")
  val updatedEntity = entity.copy(status = "INACTIVE")
  val service = ItemStatusService

  test("Create Item Status"){
    val result = Await.result(service.save(entity),2 minutes)
    assert(result.isExhausted)


  }

  test{"Read  Item Value "}{
    val result = Await.result(service.getStatus("1"),2 minutes)
    assert(result.head.status=="ACTIVE")

  }

  test("Update Item Status"){
    val update = Await.result(service.save(updatedEntity),2 minutes)
    val result = Await.result(service.getStatus("1"),2 minutes)
    assert(result.head.status=="INACTIVE")
  }

}
