package api.users

import java.time.{LocalDateTime => Date}

import domain.users.User
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class UserControllerTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : User = _
  var baseUrl = "/users/user/"
  val title = "user"

  before {
    entity = User("CPUT","test@test.com","First Name","Last Name",None,
      Some("CODER"),"test123","ACTIVE",Date.now() )

  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString())
  }

  test("update "+title){
    updateEntity = entity.copy(firstName = "james",password = "password")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title){
    val request = route(app, FakeRequest(GET, baseUrl + entity.org +"/" +entity.email)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+ " by email"){
    val request = route(app, FakeRequest(GET, baseUrl + entity.email)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

  test("get all organisation "+title+"'s"){
    val request = route(app, FakeRequest(GET, baseUrl+"org/"+entity.org)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
  }

}
