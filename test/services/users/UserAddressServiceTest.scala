package services.users

import java.time.LocalDateTime

import domain.users.UserAddress
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class UserAddressServiceTest extends FunSuite with BeforeAndAfter  {

  val service = UserAddressService
  var entity, updateEntity: UserAddress = _
  var kwargs: Map[String,String] =  _

  before{
    entity = factories.getUserAddress
    kwargs = Map("emailId"->entity.emailId, "id"->entity.id)
  }

  test("Create USER_Address"){
    val resp = Await.result(service.save(entity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_Address"){
    val resp = Await.result(service.getById(kwargs),2.minutes)
    assert(resp.get equals entity)
  }

  test("UPDATE USER_Address"){
    updateEntity = entity.copy(postalCode = "3276",date = LocalDateTime.now())
    val update  = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs),2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)

  }

  test("GET ALL USER_ADDRESS"){
    val saveResp = Await.result(service.save(entity.copy(id = "9",postalCode = "3658")), 2.minutes)
    val resp = Await.result(service.getAll(entity.emailId),2.minutes)

    assert(resp.nonEmpty)
    assert(saveResp.isExhausted)
  }

}
