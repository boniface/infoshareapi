package api.location

import domain.location.LocationType
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class LocationTypeCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : LocationType = _
  var baseUrl = "/location/locationtype/"
  val title = "location type"

  before{
    entity = LocationType(id = "1", name = "abbey cape town", code = "321", state = "Active")
  }

  test("Create "+title){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

  test("update "+title){
    updateEntity = entity.copy(code = "7580",state = "Inactive")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get "+title+" by id"){
    val request = route(app, FakeRequest(GET, baseUrl+entity.id)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all "+title){
    val request = route(app, FakeRequest(GET, baseUrl+"all")
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
  }

}
