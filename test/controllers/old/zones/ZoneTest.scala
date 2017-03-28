package controllers.old.zones

import domain.old.zones.Zone
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/02/18.
  */
class ZoneTest extends PlaySpec with GuiceOneAppPerTest {

  "ZoneController" should {


    "Create Zone " in {
      val zone = Zone("ZM", "ZM", "ACTIVE", "url")
      val request = route(app, FakeRequest(POST, "/zones/create")
        .withJsonBody(Json.toJson(zone))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }


    "display Zone type" in {
      val id = "ZM"
      val request = route(app, FakeRequest(GET, "/zones/" + id)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))

    }

    "display Zones type" in {

      val request = route(app, FakeRequest(GET, "/zones/all")
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))

    }

    "Update Zone " in {
      val zone = Zone("ZM", "ZM", "INACTIVE", "LOGO")
      val request = route(app, FakeRequest(POST, "/zones/update")
        .withJsonBody(Json.toJson(zone))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }

  }
}
