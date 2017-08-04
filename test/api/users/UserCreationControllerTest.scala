package api.users

import conf.util.RolesID
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class UserCreationControllerTest extends PlaySpec with GuiceOneAppPerTest {

  "UserCreationController" should {
    val user = factories.getUser.copy(email = "thulehadebe@outlook.com", firstName = Some("Thulebona"),
      lastName = Some("Hadebe"))

    "Create User" in {
      val request = route(app, FakeRequest(POST, "/users/usercreation/create/" + RolesID.READER)
        .withJsonBody(Json.toJson(user))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      println(" The Content is ", contentAsString(request))
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }
  }
}


