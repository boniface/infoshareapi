package api.login

import domain.security.Credential
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/06/29.
  */
class AuthenticateAccountTest extends PlaySpec with GuiceOneAppPerTest {

  "LoginController" should {

    "Given an  Accounts when and Email is Supplied" in {
      val email = "YOUR EMAIL"
      val entity = Credential(email,"PASSWD","CPUT")

      val request = route(app, FakeRequest(POST, "/login/authenticate/")
        .withJsonBody(Json.toJson(entity))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }


  }

}
