package services.util
import domain.util.Keys
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2017/04/22.
  */
class KeyServiceTest extends FunSuite{


  val entity = Keys("1","CDN_URL","ACTIVE")
  val updatedEntity = entity.copy(status = "INACTIVE")
  val service = KeysService

  test("Create Key"){
    val result = Await.result(service.save(entity),2 minutes)
    assert(result.isExhausted)

  }

  test{"Read Key value "}{
    val result = Await.result(service.getKeyById("1"),2 minutes)
    assert(result.get.status=="ACTIVE")

  }

  test("Upadte Key"){
    val update = Await.result(service.save(updatedEntity),2 minutes)
    val result = Await.result(service.getKeyById("1"),2 minutes)
    assert(result.get.status=="INACTIVE")

  }

}
