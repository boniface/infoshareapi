package services.users

import java.util.Date

import domain.users.UserImages
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration._


class UserImageServiceTest extends FunSuite with BeforeAndAfter{

  val userImgService = UserImageService
  var userImgEntity, updateEntity: UserImages = _
  var kwargs: Map[String,String] = _

  before{
    userImgEntity = UserImages(org = "CPUT", emailId = "test@email.com", id="2", description = "my pic",
                  url="http://www.cput.ac.za/logo.png", mime = ".png", size = None ,date = new Date())
    kwargs = Map("org"->userImgEntity.org,"emailId"->userImgEntity.emailId,"id"->userImgEntity.id)

  }

  test("Create USER_IMAGE"){
    val resp = Await.result(userImgService.save(userImgEntity), 2.minutes)
    assert(resp.isExhausted)
  }

  test("Get USER_IMAGE BY_ID"){
    val resp = Await.result( userImgService.getUserImageById(kwargs), 2.minutes)
    assert(resp.head.org equals userImgEntity.org)
    assert(resp.head.emailId equals userImgEntity.emailId)
    assert(resp.head.id equals userImgEntity.id)
    assert(resp.head.description equals userImgEntity.description)
    assert(resp.head.url equals userImgEntity.url)
    assert(resp.head.mime equals userImgEntity.mime)
    assert(resp.head.size equals userImgEntity.size)
  }

  test("Update USER_IMAGE "){
    updateEntity = userImgEntity.copy(mime= ".jpeg", size = Some("15MB"))
    val update = Await.result(userImgService.save(updateEntity), 2.minutes)
    assert(update.isExhausted)

    val resp = Await.result( userImgService.getUserImageById(kwargs), 2.minutes)

    assert(resp.head.org equals userImgEntity.org)
    assert(resp.head.emailId equals userImgEntity.emailId)
    assert(resp.head.id equals updateEntity.id)
    assert(resp.head.mime equals updateEntity.mime)
    assert(resp.head.size equals updateEntity.size)
  }

  test("Get ALL USER_IMAGES"){
    val resp2 = Await.result(userImgService.save(userImgEntity.copy(id="3")), 2.minutes)
    val resp = Await.result( userImgService.getAllUserImages(kwargs), 2.minutes)

    assert(resp2.isExhausted)
    assert(resp.size > 1)
  }

  test("Get ALL USER ORG_IMG"){
    updateEntity = userImgEntity.copy(id= "4",emailId = "me2@test.com")
    val resp2 = Await.result(userImgService.save(updateEntity), 2.minutes)
    val resp = Await.result( userImgService.getAllUserCompanyImages(kwargs("org")), 2.minutes)

    assert(resp2.isExhausted)
    assert(resp.size > 2)
  }

}

