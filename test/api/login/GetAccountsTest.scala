package api.login

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._
/**
  * Created by hashcode on 2017/06/29.
  */
class GetAccountsTest extends PlaySpec with GuiceOneAppPerTest{

  "LoginController" should {

    "Give you Accounts when and Email is Supplied" in {
      val email = "boniface@kabaso.com"

      val request = route(app, FakeRequest(GET, "/login/accounts/"+email)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }
  }

}
