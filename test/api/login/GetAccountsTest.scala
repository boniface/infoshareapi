package api.login

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.TestUtils
/**
  * Created by hashcode on 2017/06/29.
  */
class GetAccountsTest extends PlaySpec with GuiceOneAppPerTest{


  "LoginController" should {

    "Given you Accounts when and Email is Supplied" in {
      val email = "test@test.com"

      val request = route(app, FakeRequest(GET, "/login/accounts/"+email)
        .withHeaders(TestUtils.getHeaders:_*)
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }
  }

}
