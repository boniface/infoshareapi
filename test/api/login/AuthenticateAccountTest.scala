package api.login

import domain.security.Credential
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

/**
  * Created by hashcode on 2017/06/29.
  */
class AuthenticateAccountTest extends PlaySpec with GuiceOneAppPerTest {

  "LoginController" should {

    "Given a token when credentials are Supplied" in {
      val user = factories.getUser
      val entity = Credential(email = user.email, password = user.password, siteId = user.siteId)

      val request = route(app, FakeRequest(POST, "/login/authenticate/")
        .withJsonBody(Json.toJson(entity))
        .withHeaders(TestUtils.getHeaders:_*)
      ).get

      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }
  }
}
