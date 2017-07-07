package api.content

import java.time.LocalDateTime

import domain.content.PublishedContent
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.TestUtils

class PublishedContentCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : PublishedContent = _
  var baseUrl = "/content/published/"

  before {
    entity =  PublishedContent(org="DUT", id ="1", dateCreated= LocalDateTime.now(), creator="test@me.com", source="3", category ="3",
      title = "birth control", content = "we not animals", contentType = "Text/Image",
      status = "published",  state ="active")

  }

  test("Create published content"){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(TestUtils.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson( entity).toString())
  }

  test("update published content"){
    updateEntity =  entity.copy(contentType="images")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson( entity).toString())
    assert(contentAsString(request)  equals Json.toJson(updateEntity).toString())
  }

  test("get published content by id"){
    val request = route(app, FakeRequest(GET, baseUrl+ entity.org+ "/" +entity.id)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }


  test("get paginated published content"){
    val request = route(app, FakeRequest(GET, baseUrl+"range/"+entity.org+"/"+2)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    println(contentAsString(request))
  }

  test("get all published content"){
    val request = route(app, FakeRequest(GET, baseUrl+"all/"+entity.org)
      .withHeaders(TestUtils.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    println(contentAsString(request))
  }

}
