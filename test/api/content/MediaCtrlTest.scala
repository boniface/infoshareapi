package api.content

import domain.content.Media
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class MediaCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : Media = _
  val baseUrl = "/content/media/"
  val title = "Media"

  before{
    entity = factories.getMedia
  }
  title + "Controller " should {

    "Create " + title in {
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(toJson(entity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(entity).toString())
    }

    "update "+ title in {
      updateEntity = entity.copy(url = "https://www.infoshare.com/image.png", mime = ".png")
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(toJson(updateEntity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) != toJson(entity).toString())
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get " + title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + entity.contentId + "/" + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get all " + title in {
      val request = route(app, FakeRequest(GET, baseUrl + "all/" + entity.contentId)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(!contentAsString(request).isEmpty)
    }
  }
}
