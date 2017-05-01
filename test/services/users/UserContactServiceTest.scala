package services.users

import java.util.Date

import domain.users.UserContact
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class UserContactServiceTest extends FunSuite with BeforeAndAfter  {

  val userContactService = UserContactService
  var userContactEntity, updateEntity: UserContact = _

  before{
    userContactEntity = UserContact(emailId = "test@test.com",id= "1", addressTypeId = "1",
      contactNumber = "+2774 791 3185", date = new Date(), state = "ACTIVE")
  }

  test("Create USER_Contact"){
    val resp = Await.result(userContactService.save(userContactEntity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_Contact"){
    val resp = Await.result(userContactService.getById(Map("emailId"-> userContactEntity.emailId,"id"-> userContactEntity.id)),2.minutes)

    assert(resp.head.id == userContactEntity.id)
    assert(resp.head.emailId  == userContactEntity.emailId)
    assert(resp.head.addressTypeId == userContactEntity.addressTypeId)
    assert(resp.head.contactNumber  == userContactEntity.contactNumber)
    assert(resp.head.state  == userContactEntity.state)

  }

  test("UPDATE USER_Contact"){
    updateEntity = userContactEntity.copy(contactNumber = "021 784 3598",date = new Date())
    val update = Await.result(userContactService.save(updateEntity), 2.minutes)
    assert(update.isExhausted)

    val resp = Await.result(userContactService.getById(Map("emailId"-> updateEntity.emailId,"id"-> updateEntity.id)),2.minutes)

    assert(resp.head.id == updateEntity.id)
    assert(resp.head.contactNumber  == updateEntity.contactNumber)

  }

  test("GET ALL USER_Contact"){
    val saveResp = Await.result(userContactService.save(updateEntity.copy(id = "9",contactNumber = "083 995 78452")), 2.minutes)
    val resp = Await.result(userContactService.getAllUserContacts(updateEntity.emailId),2.minutes)
    assert(resp.size > 1)
    assert(saveResp.isExhausted)
  }

}
