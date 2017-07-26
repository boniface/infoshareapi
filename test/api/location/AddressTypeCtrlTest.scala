package api.location

import domain.location.AddressType
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class AddressTypeCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : AddressType = _
  var baseUrl = "/location/addresstype/"
  val title = "Address type"

  before{
    entity = factories.getAddressType
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

    "update " + title in{
      updateEntity = entity.copy(name = "Work")
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
  }

}
