package api.content

import domain.content.ContentType
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ContentTypeCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : ContentType = _
  var baseUrl = "/content/"
  before{
     entity = ContentType(id = "1", name = "TEXT", description = "text content")
  }

  test("Create content type"){
    val request = route(app, FakeRequest(POST, baseUrl + "contenttype/create")
      .withJsonBody(Json.toJson( entity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson( entity).toString() )
  }

  test("update content type"){
    updateEntity =  entity.copy(name="images")
    val request = route(app, FakeRequest(POST, baseUrl+"contenttype/create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson( entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get content type by id"){
    val request = route(app, FakeRequest(GET, baseUrl+"contenttype/"+ entity.id)
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all content type"){
    val request = route(app, FakeRequest(GET, baseUrl+"contenttypes")
      .withHeaders(AUTHORIZATION -> "Token")
    ).get
    assert(status(request) equals OK)
    println(contentAsString(request))
  }

}
