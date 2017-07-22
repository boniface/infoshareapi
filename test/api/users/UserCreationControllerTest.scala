package api.users

import java.time.{LocalDateTime => Date}

import domain.security.RolesID
import domain.users.User
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class UserCreationControllerTest extends PlaySpec with GuiceOneAppPerTest {


  "UserCreationController" should {

    "Create Zone " in {
      val user = User("CPUT",
        "boniface@kabaso.com",
        Some("Boniface"),
        Some("kabaso"),
        "Geek",
        "test123",
        "ACTIVE",
        Date.now())
      val request = route(app, FakeRequest(POST, "/users/usercreation/" + "create/" + RolesID.READER)
        .withJsonBody(Json.toJson(user))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }

  }
}



