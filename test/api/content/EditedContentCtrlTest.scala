package api.content

import domain.content.EditedContent
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import java.time.LocalDateTime
import play.api.test.Helpers._
import util.UtilTest

class EditedContentCtrlTest extends FunSuite with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : EditedContent = _
  var baseUrl = "/content/edited/"

  before {
    entity =  EditedContent(org="DUT", id ="1", dateCreated= LocalDateTime.now(), creator="test@me.com", source="3", category ="3",
      title = "birth control", content = "we not animals", contentType = "Text/Image",
      status = "edited",  state ="active")

  }

  test("Create edited content"){
    val request = route(app, FakeRequest(POST, baseUrl + "create")
      .withJsonBody(Json.toJson(entity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get

    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson( entity).toString())
  }

  test("update edited content"){
    updateEntity =  entity.copy(contentType="images")
    val request = route(app, FakeRequest(POST, baseUrl+"create")
      .withJsonBody(Json.toJson(updateEntity))
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) != Json.toJson( entity).toString())
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get edited content by id"){
    val request = route(app, FakeRequest(GET, baseUrl+ entity.org+ "/" +entity.id)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
  }

  test("get paginated edited content"){
    val request = route(app, FakeRequest(GET, baseUrl+"range/"+entity.org+"/"+2)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    println(contentAsString(request))
  }

  test("get all edited content"){
    val request = route(app, FakeRequest(GET, baseUrl+"all/"+entity.org)
      .withHeaders(UtilTest.getHeaders:_*)
    ).get
    assert(status(request) equals OK)
    println(contentAsString(request))
  }

}
