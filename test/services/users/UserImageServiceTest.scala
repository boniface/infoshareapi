package services.users

import java.time.LocalDateTime

import domain.users.UserImages
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.factories

import scala.concurrent.Await
import scala.concurrent.duration._


class UserImageServiceTest extends FunSuite with BeforeAndAfter{

  val service = UserImageService
  var entity, updateEntity: UserImages = _
  var kwargs: Map[String,String] = _

  before{
    entity = factories.getUserImages
    kwargs = Map("org"->entity.org,"emailId"->entity.emailId,"id"->entity.id)
  }

  test("Create USER_IMAGE"){
    val resp = Await.result(service.save(entity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_IMAGE BY_ID"){
    val resp = Await.result( service.getUserImageById(kwargs), 2.minutes)
    assert(resp.get equals entity)
  }

  test("Update USER_IMAGE "){
    updateEntity = entity.copy(mime= ".jpeg", size = Some("15MB"))
    val update = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result( service.getUserImageById(kwargs), 2.minutes)

    assert(update.isExhausted)
    assert(resp.get != entity)
    assert(resp.get equals updateEntity)
  }

  test("Get ALL USER_IMAGES"){
    val resp2 = Await.result(service.save(entity.copy(id="3")), 2.minutes)
    val resp = Await.result( service.getAllUserImages(kwargs), 2.minutes)

    assert(resp.nonEmpty)
    assert(resp2.isExhausted)
  }

  test("Get ALL USER ORG_IMG"){
    updateEntity = entity.copy(id= "4",emailId = "test2@test.com")
    val resp2 = Await.result(service.save(updateEntity), 2.minutes)
    val resp = Await.result( service.getAllUserCompanyImages(kwargs("org")), 2.minutes)

    assert(resp.nonEmpty)
    assert(resp2.isExhausted)
  }

}

