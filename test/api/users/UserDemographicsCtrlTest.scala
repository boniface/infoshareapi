package api.users

import domain.users.UserDemographics
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class UserDemographicsCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity, updateEntity : UserDemographics = _
  var baseUrl = "/users/demographics/"
  val title = "demographics"

  before {
    entity =  factories.getUserDemographics
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
      updateEntity = entity.copy(genderId = "12", raceId = "2", maritalStatusId = "1")
      val request = route(app, FakeRequest(POST, baseUrl + "create")
        .withJsonBody(Json.toJson(updateEntity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(contentAsString(request) != Json.toJson(entity).toString())
      assert(contentAsString(request) equals Json.toJson(updateEntity).toString())
    }


    "get " + title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + entity.emailId + "/" + entity.id)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      assert(!contentAsJson(request).result.isEmpty)
    }

    "get all organisation " + title + "'s" in {
      val request = route(app, FakeRequest(GET, baseUrl + "all/" + entity.emailId)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get
      assert(status(request) equals OK)
    }
  }
}
