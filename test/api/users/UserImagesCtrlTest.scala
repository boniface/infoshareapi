package api.users

import java.time.LocalDateTime

import domain.users.UserImages
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.UtilTest

class UserImagesCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : UserImages = _
  var baseUrl = "/users/image/"
  val title = "user image"

  before{
    entity =  UserImages(org = "CPUT", emailId = "test@email.com", id="2", description = "my pic",
      url="http://www.cput.ac.za/logo.png", mime = ".png", size = None ,date = LocalDateTime.now())
  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

  test("update "+title){
    updateEntity = entity.copy(size= Some("7530"), description= "cput logo")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+" by id"){ //user/$org/$emailId
    val request = route(app, FakeRequest(GET, baseUrl + entity.org + "/" + entity.emailId + "/" + entity.id)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl + "user/" + entity.org + "/" + entity.emailId)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

  test("get all org images"){
    val request = route(app, FakeRequest(GET, baseUrl + "org/" + entity.org)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

}
