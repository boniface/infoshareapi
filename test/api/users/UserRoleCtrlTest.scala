package api.users

import domain.users.UserRole
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class UserRoleCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : UserRole = _
  var baseUrl = "/users/role/"
  val title = "user role"
/*
 // user role
    case GET(p"/role/all/$emailId") =>
      userRoleCtrl.getAll(emailId)
    case GET(p"/role/$emailId/$id") =>
      userRoleCtrl.getById(emailId, id)
    case GET(p"/role/delete/$emailId/$id") =>
      userRoleCtrl.delete(emailId, id)
 */
//  before{
//    entity =  UserRole(emailId="test@test.com",roleId = "1", state="ACTIVE")
//  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

//  test("update "+title){
//    updateEntity = entity.copy(state = "INACTIVE")
//    val request = route(app, FakeRequest(POST, baseUrl + "create")
//      .withJsonBody(Json.toJson(updateEntity))
//      .withHeaders(AUTHORIZATION -> "Token")
//    ).get
//    assert(status(request) equals OK)
//    assert(contentAsString(request) != Json.toJson(entity).toString())
//    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
//  }

  test("get "+title+" by id"){
    val request = route(app, FakeRequest(GET, baseUrl + entity.emailId + "/" + entity.roleId)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl + "all/" + entity.emailId)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

  test("delete "+title){
    val request = route(app, FakeRequest(GET, baseUrl + "delete/" + entity.emailId +"/" + entity.roleId)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(!contentAsJson(request).result.isEmpty)
  }

}
