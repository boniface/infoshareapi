package api.content

import java.time.LocalDateTime

import domain.content.Media
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.UtilTest

class MediaCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : Media = _
  var baseUrl = "/content/media/"
  before{
    entity = Media(contentId = "1", id = "1", description = "birth control",url = "https:me.com/jpg",
      mime = ".jpg",date = LocalDateTime.now(),state = "Active")
  }
  test("Create media"){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(entity).toString() )
  }

  test("update media"){
    updateEntity = entity.copy(url = "https://www.me.com/me.png",mime = ".png")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson(entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get media by id"){
    val request = route(app, FakeRequest(GET, baseUrl+ entity.contentId +"/"+entity.id)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get all media"){
    val request = route(app, FakeRequest(GET, baseUrl+ "all/" + entity.contentId)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
  }

}
