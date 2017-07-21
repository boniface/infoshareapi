package services.util

import domain.util.ItemStatus
import java.time.LocalDateTime

import conf.util.HashcodeKeys
import org.scalatest.FunSuite
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/04/22.
  */
class ItemStatusServiceTest extends FunSuite{


  val entity: ItemStatus = factories.getItemStatus
  val updatedEntity: ItemStatus = entity.copy(status = HashcodeKeys.INACTIVE)
  val service = ItemStatusService

  test("Create Item Status"){
    val result = Await.result(service.save(entity),2 minutes)
    assert(result.isExhausted)
  }

  test("Read  Item Value "){
    val result = Await.result(service.getStatus("1"),2 minutes)
    assert(result.head.status=="ACTIVE")

  }

  test("Update Item Status"){
    val update = Await.result(service.save(updatedEntity),2 minutes)
    val result = Await.result(service.getStatus("1"),2 minutes)
    assert(result.head.status== HashcodeKeys.INACTIVE)
  }

}
