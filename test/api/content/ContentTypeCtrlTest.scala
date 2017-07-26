package api.content

import domain.content.ContentType
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class ContentTypeCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : ContentType = _
  val baseUrl = "/content/"
  val title = "Content Type"

  before{
     entity = factories.getContentType
  }

  title + " Controller " should {

    "Create "+ title in {
      val request = route(app, FakeRequest(POST, baseUrl + "contenttype/create")
        .withJsonBody(toJson(entity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(entity).toString())
    }

    "update " + title in {
      updateEntity = entity.copy(name = "images")
      val request = route(app, FakeRequest(POST, baseUrl + "contenttype/create")
        .withJsonBody(toJson(updateEntity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) != toJson(entity).toString())
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get " + title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + "contenttype/" + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get all " + title + "s" in {
      val request = route(app, FakeRequest(GET, baseUrl + "contenttypes")
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(!contentAsString(request).isEmpty)
    }
  }
}
