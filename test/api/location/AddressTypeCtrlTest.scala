package api.location

import domain.location.AddressType
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.UtilTest

class AddressTypeCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : AddressType = _
  var baseUrl = "/location/addresstype/"
  val title = "address type"

  before{
    entity = AddressType(id = "1", name = "Home", state = "Active")
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
    updateEntity = entity.copy(name="Work")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+" by id"){
    val request = route(app, FakeRequest(GET, baseUrl+entity.id)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl+"all")
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
  }

}
