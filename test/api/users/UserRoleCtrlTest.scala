package api.users

import domain.users.UserRole
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.UtilTest

class UserRoleCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : UserRole = _
  var baseUrl = "/users/role/"
  val title = "user role"

//  before{
//    entity =  UserRole(emailId="test@test.com",roleId = "1", state="ACTIVE")
//  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

//  test("update "+title){
//    updateEntity = entity.copy(state = "INACTIVE")
//    val request = route(app, FakeRequest(POST, baseUrl + "create")
//      .withJsonBody(Json.toJson(updateEntity))
//      .withHeaders(UtilTest.getHeaders:_*)
//    ).get
//    assert(status(request) equals OK)
//    assert(contentAsString(request) != Json.toJson(entity).toString())
//    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
//  }

  test("get "+title+" by id"){
    val request = route(app, FakeRequest(GET, baseUrl + entity.emailId + "/" + entity.roleId)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl + "all/" + entity.emailId)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

  test("delete "+title){
    val request = route(app, FakeRequest(GET, baseUrl + "delete/" + entity.emailId +"/" + entity.roleId)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

}
