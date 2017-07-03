package api.users

import java.time.LocalDateTime

import domain.security.RolesID
import domain.users.User
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.UtilTest

class UserCreationControllerTest extends PlaySpec with GuiceOneAppPerTest {


  "UserCreationController" should {

    "Create Zone " in {
      val user = User("CPUT",
        "boniface@kabaso.com",
        Some("Boniface"),
        Some("Kabaso"),
        None,
        "Geek",
        "test123",
        "ACTIVE",
        LocalDateTime.now())
      val request = route(app, FakeRequest(POST, "/users/usercreation/" + "create/" + RolesID.READER)
        .withJsonBody(Json.toJson(user))
        .withHeaders(UtilTest.getHeaders:_*)
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }

  }
}



