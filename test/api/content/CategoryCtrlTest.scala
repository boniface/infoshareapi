package api.content

import domain.content.Category
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class CategoryCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : Category = _
  var baseUrl = "/content/"
  before{
    entity = Category(id="1",name="HIV PREVENTION", description="how to prevent HIV")
  }

  test("Create category"){
    val request = route(app, FakeRequest(POST, baseUrl + "category/create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

  test("update category"){
    updateEntity = entity.copy(name="CANCER PREVENTION")
    val request = route(app, FakeRequest(POST, baseUrl+"category/create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get category by id"){
    val request = route(app, FakeRequest(GET, baseUrl+"category/"+entity.id)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all category"){
    val request = route(app, FakeRequest(GET, baseUrl+"categories")
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
  }

}
