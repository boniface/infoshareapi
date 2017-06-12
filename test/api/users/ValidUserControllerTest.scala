package api.users

import java.time.LocalDateTime

import conf.util.enums.Events
import domain.users.ValidUser
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/06/11.
  */
class ValidUserControllerTest extends PlaySpec with GuiceOneAppPerTest {

  "Valid User Controller " should {
    val userId = "test@test.com"

    "Create Valid User Record" in {
      val validUser = ValidUser(userId,LocalDateTime.now,Events.VALIDATED)
      val request = route(app, FakeRequest(POST, "/users/valid/create")
        .withJsonBody(Json.toJson(validUser))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }


    "Test if  User is Valid " in {
      val request = route(app, FakeRequest(GET, "/users/valid/user/"+userId)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }


    "Get Valid User Events  " in {
      val request = route(app, FakeRequest(GET, "/users/valid/events/"+userId)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }


    "Get Number of Valid Users " in {
      val request = route(app, FakeRequest(GET, "/users/valid/users")
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }

  }

}
