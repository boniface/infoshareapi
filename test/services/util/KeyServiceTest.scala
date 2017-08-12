package services.util
import conf.util.HashcodeKeys
import domain.util.Keys
import org.scalatest.FunSuite
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/04/22.
  */
class KeyServiceTest extends FunSuite{


  val entity = factories.getKeys
  val updatedEntity = entity.copy(status = HashcodeKeys.INACTIVE)
  val service = KeysService

  test("Create Key"){
    val result = Await.result(service.save(entity),2 minutes)
    assert(result.isExhausted)

  }

  test("Read Key value "){
    val result = Await.result(service.getKeyById("1"),2 minutes)
    assert(result.get.status=="ACTIVE")

  }

  test("Update Key"){
    val update = Await.result(service.save(updatedEntity),2 minutes)
    val result = Await.result(service.getKeyById("1"),2 minutes)
    assert(result.get.status=="INACTIVE")

  }

  test("get all keys"){
    val result = Await.result(service.getAllkeys,2 minutes)
    assert(result.nonEmpty)
  }

}
