package api.users

import domain.users.User
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class UserControllerTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : User = _
  var baseUrl = "/users/user/"
  val title = "user"

  before {
    entity = factories.getUser
  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(TestUtils.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString())
  }


  test("get "+title){
    val request = route(app, FakeRequest(GET, baseUrl + entity.siteId +"/" +entity.email)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+ " by email"){
    val request = route(app, FakeRequest(GET, baseUrl + entity.email)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

  test("get all organisation "+title+"'s"){
    val request = route(app, FakeRequest(GET, baseUrl+"org/"+entity.siteId)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
  }

}
