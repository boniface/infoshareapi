package controllers.util

import domain.util.{RoleValues, Roles}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/03/10.
  */
class RoleControllerTest extends PlaySpec with GuiceOneAppPerTest {

  "RoleController" should {

    "Create Role " in {
      val role = Roles(RoleValues.ANONYMOUS, RoleValues.ANONYMOUS)
      val request = route(app, FakeRequest(POST, "/util/roles/create")
        .withJsonBody(Json.toJson(role))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }


    "Get a Single Role " in {
      val id = RoleValues.ANONYMOUS
      val request = route(app, FakeRequest(GET, "/util/roles/" + id)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))

    }

    "Get ALL Roles " in {
      val request = route(app, FakeRequest(GET, "/util/roles/all")
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))
    }
  }

}
