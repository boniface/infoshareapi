package api.users

import domain.users.UserImages
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class UserImagesCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : UserImages = _
  var baseUrl = "/users/image/"
  val title = "User image"

  before{
    entity = factories.getUserImages
  }

  title + " Controller " should {

    "Create " + title in {
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(Json.toJson(entity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals Json.toJson(entity).toString())
    }

    "update " + title in {
      updateEntity = entity.copy(size = Some("7530"), description = "cput logo")
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(Json.toJson(updateEntity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) != Json.toJson(entity).toString())
      assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
    }

    "get " + title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + entity.org + "/" + entity.emailId + "/" + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
    }

    "get all " + title in {
      val request = route(app, FakeRequest(GET, baseUrl + "user/" + entity.org + "/" + entity.emailId)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get
      assert(status(request) equals OK)
      assert(contentAsJson(request).result.isDefined)
    }

    "get all org " + title + "'s" in {
      val request = route(app, FakeRequest(GET, baseUrl + "org/" + entity.org)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(!contentAsString(request).isEmpty)
    }
  }
}
