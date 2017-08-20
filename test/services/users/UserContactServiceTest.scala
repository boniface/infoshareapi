package services.users

import java.time.LocalDateTime

import domain.users.UserContact
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class UserContactServiceTest extends FunSuite with BeforeAndAfter  {

  val service = UserContactService
  var entity, updateEntity: UserContact = _
  var kwargs: Map[String,String] =  _

  before{
    entity = factories.getUserContact
    kwargs = Map("emailId"->entity.emailId, "id"->entity.id)
  }

  test("Create USER_Contact"){
    val resp = Await.result(service.save(entity),2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_Contact"){
    val resp = Await.result(service.getById(kwargs),2.minutes)
    assert(resp.get equals entity)

  }

  test("UPDATE USER_Contact"){
    updateEntity = entity.copy(contactNumber = "021 784 3598",date = LocalDateTime.now())
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result(service.getById(kwargs),2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)

  }

  test("GET ALL USER_Contact"){
    val saveResp = Await.result(service.save(updateEntity.copy(id = "9",contactNumber = "083 995 78452")), 2.minutes)
    val resp = Await.result(service.getAllUserContacts(kwargs("emailId")),2.minutes)

    assert(resp.nonEmpty)
    assert(saveResp.isExhausted)
  }

}
