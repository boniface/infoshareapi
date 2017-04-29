package services.person

import java.util.Date

import domain.person.UserAddress
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class UserAddressServiceTest extends FunSuite with BeforeAndAfter  {

  val userAddressService = UserAddressService
  var userAddressEntity, updateEntity: UserAddress = _

  before{
    userAddressEntity = UserAddress("test@test.com", "6", "1", "my address", "3275", new Date(), "ACTIVE")
  }

  test("Create USER_Address"){
    val resp = Await.result(userAddressService.save(userAddressEntity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_Address"){
    val resp = Await.result(userAddressService.getAddrById(Map("userId"-> userAddressEntity.userId,"id"-> userAddressEntity.id)),2.minutes)

    assert(resp.head.id == userAddressEntity.id)
    assert(resp.head.userId  == userAddressEntity.userId)
    assert(resp.head.description == userAddressEntity.description)
    assert(resp.head.postalCode  == userAddressEntity.postalCode)
    assert(resp.head.addressTypeId  == userAddressEntity.addressTypeId)
    assert(resp.head.state  == userAddressEntity.state)

  }

  test("UPDATE USER_Address"){
    updateEntity = userAddressEntity.copy(postalCode = "3276",date = new Date())
    Await.result(userAddressService.save(updateEntity), 2.minutes)
    val resp = Await.result(userAddressService.getAddrById(Map("userId"-> userAddressEntity.userId,"id"-> userAddressEntity.id)),2.minutes)

    assert(resp.head.id == userAddressEntity.id)
    assert(resp.head.userId  == userAddressEntity.userId)
    assert(resp.head.description == userAddressEntity.description)
    assert(resp.head.postalCode  == updateEntity.postalCode)
    assert(resp.head.addressTypeId  == userAddressEntity.addressTypeId)
    assert(resp.head.date.toString  == updateEntity.date.toString)
    assert(resp.head.state  == userAddressEntity.state)

  }

  test("GET ALL USER_ADDRESS"){
    val saveResp = Await.result(userAddressService.save(userAddressEntity.copy(id = "9",postalCode = "3658")), 2.minutes)
    val resp = Await.result(userAddressService.getUserAllAddress(userAddressEntity.userId),2.minutes)
    assert(resp.size > 1)
    assert(saveResp.isExhausted)
  }

}
