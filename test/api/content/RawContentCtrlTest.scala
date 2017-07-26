package api.content

import domain.content.RawContent
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class RawContentCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : RawContent = _
  var baseUrl = "/content/raw/"
  val title = "Raw Content"

  before {
    entity = factories.getRawContent
  }

  title + " Controller " should {

    "Create " + title in {
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(toJson(entity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(entity).toString())
    }

    "update " + title in {
      updateEntity = entity.copy(contentTypeId = "Text/Images")
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(toJson(updateEntity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) != toJson(entity).toString())
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get " + title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + entity.org + "/" + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get paginated "+ title in { // TODO: fix the range test
      val request = route(app, FakeRequest(GET, baseUrl + "range/" + entity.org + "/" + 2)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      println(contentAsString(request))
    }

    "get all " + title in {
      val request = route(app, FakeRequest(GET, baseUrl + "all/" + entity.org)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(!contentAsString(request).isEmpty)
    }
  }
}
