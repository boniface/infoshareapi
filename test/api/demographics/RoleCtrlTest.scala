package api.demographics

import conf.util.RolesID
import domain.demographics.Role
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.TestUtils

class RoleCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : Role = _
  var baseUrl = "/demo/role/"
  val title = "Role"

  before{
    entity = Role(id = "1", name = RolesID.READER, description = "role", state = "Active")
  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(TestUtils.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

  test("update "+title){
    updateEntity = entity.copy(name=RolesID.ADMIN)
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+" by id"){
    val request = route(app, FakeRequest(GET, baseUrl+entity.id)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl+"all")
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
  }


  test("delete "+title){
    val request = route(app, FakeRequest(POST, baseUrl+"delete/"+entity.id)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
  }

}
