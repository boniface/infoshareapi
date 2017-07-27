package api.content

import domain.content.Category
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class CategoryCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : Category = _
  val baseUrl = "/content/"
  val title = "Category"

  before{
    entity = factories.getCategory
  }

  title + " Controller " should {

    "Create a "+ title in {
      val request = route(app, FakeRequest(POST, baseUrl + "category/create")
        .withJsonBody(toJson(entity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(entity).toString())
    }

    "update " + title in {
      updateEntity = entity.copy(name = "CANCER PREVENTION")
      val request = route(app, FakeRequest(POST, baseUrl + "category/create")
        .withJsonBody(toJson(updateEntity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) != toJson(entity).toString())
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get "+ title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + "category/" + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      println(contentAsString(request))
      assert(status(request) equals OK)
      assert(contentAsString(request) equals toJson(updateEntity).toString())
    }

    "get all "+ title in {
      val request = route(app, FakeRequest(GET, baseUrl + "categories")
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(!contentAsString(request).isEmpty)
    }
  }
}
