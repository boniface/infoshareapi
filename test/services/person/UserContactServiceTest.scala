package services.person

import java.util.Date

import domain.person.UserContact
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class UserContactServiceTest extends FunSuite with BeforeAndAfter  {

  val userContactService = UserContactService
  var userContactEntity, updateEntity: UserContact = _


  before{
    userContactEntity = UserContact(userId = "test@test.com",id= "1", addressTypeId = "1",
      contactNumber = "+2774 791 3185", date = new Date(), state = "ACTIVE")
  }

  test("Create USER_Contact"){
    val resp = Await.result(userContactService.save(userContactEntity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_Contact"){
    val resp = Await.result(userContactService.getById(Map("userId"-> userContactEntity.userId,"id"-> userContactEntity.id)),2.minutes)

    assert(resp.head.id == userContactEntity.id)
    assert(resp.head.userId  == userContactEntity.userId)
    assert(resp.head.addressTypeId == userContactEntity.addressTypeId)
    assert(resp.head.contactNumber  == userContactEntity.contactNumber)
    assert(resp.head.state  == userContactEntity.state)

  }

  test("UPDATE USER_Contact"){
    updateEntity = userContactEntity.copy(contactNumber = "021 784 3598",date = new Date())
    Await.result(userContactService.save(updateEntity), 2.minutes)
    val resp = Await.result(userContactService.getById(Map("userId"-> updateEntity.userId,"id"-> updateEntity.id)),2.minutes)

    assert(resp.head.id == updateEntity.id)
    assert(resp.head.contactNumber  == updateEntity.contactNumber)

  }

  test("GET ALL USER_Contact"){
    val saveResp = Await.result(userContactService.save(updateEntity.copy(id = "9",contactNumber = "083 995 78452")), 2.minutes)
    val resp = Await.result(userContactService.getAllUserContacts(updateEntity.userId),2.minutes)
    assert(resp.size > 1)
    assert(saveResp.isExhausted)
  }

}
