package api.users

import java.time.LocalDateTime

import domain.users.UserContact
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.UtilTest

class UserContactCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : UserContact = _
  var baseUrl = "/users/contact/"
  val title = "user contact"

  before {
    entity = UserContact(emailId = "test@test.com", id = "1", addressTypeId = "1",
      contactNumber = "+2774 791 3185", date = LocalDateTime.now(), state = "ACTIVE")
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
    updateEntity = entity.copy(contactNumber="074 791 3185",addressTypeId = "home address")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+" by id"){
    val request = route(app, FakeRequest(GET, baseUrl + entity.emailId + "/" + entity.id)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl+"all/"+entity.emailId)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
  }

}
