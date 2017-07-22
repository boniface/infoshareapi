package api.login

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._
/**
  * Created by hashcode on 2017/06/29.
  */
class GetAccountsTest extends PlaySpec with GuiceOneAppPerTest{

  val token = "eyJraWQiOiJQVUJMSUNLRVkiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJIQVNIQ09ERS5aTSIsImF1ZCI6IlNJVEVVU0VSUyIsImV4cCI6MTUwMDgzMDgxMSwianRpIjoiUUlYVE1yR3c2NnpFYTFpTUdGOC1XdyIsImlhdCI6MTUwMDc0NDQxMSwibmJmIjoxNTAwNzQ0MjkxLCJzdWIiOiJTaXRlIEFjY2VzcyIsImVtYWlsIjoiYm9uaWZhY2VAa2FiYXNvLmNvbSIsInJvbGUiOiJSRUFERVIiLCJvcmdDb2RlIjoiQ1BVVCIsImFnZW50IjoiJDJhJDEyJDl0QjhvUkQzRGkzQVJubmJKQjVnTS4wT0ZsM0dIVDUwbkI5WDk0SU9rVnl3ZllCUGhWNncuIn0.Z0PAd0YyKN3_rm_AKiCwXfzKEkB9AApqrodtcqVLYZZ0Zqr2CHAGdErpT0FjVvCcVkSKvSbbn8tsbFnVufy8lg"

  "LoginController" should {

    "Given you Accounts when and Email is Supplied" in {
      val email = "test@test.com"

      val request = route(app, FakeRequest(GET, "/login/accounts/"+email)
        .withHeaders(AUTHORIZATION -> token)
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }
  }

}
