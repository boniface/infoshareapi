package api.demographics

import conf.util.GenderKeys
import domain.demographics.Gender
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class GenderCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : Gender = _
  var baseUrl = "/demo/gender/"
  val title = "Gender"

  before{
    entity = factories.getGender
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
      updateEntity = entity.copy(name = GenderKeys.FEMALE)
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(Json.toJson(updateEntity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) != Json.toJson(entity).toString())
      assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
    }

    "get " + title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
    }

    "get all " + title in {
      val request = route(app, FakeRequest(GET, baseUrl + "all")
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(!contentAsString(request).isEmpty)
    }


    "delete " + title in {
      val request = route(app, FakeRequest(POST, baseUrl + "delete/" + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
    }
  }

}
