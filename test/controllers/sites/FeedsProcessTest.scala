package controllers.sites

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/03/21.
  */
class FeedsProcessTest extends PlaySpec with GuiceOneAppPerTest {

  "Process Feeds" should {

    "Process Feeds" in {
      val request = route(app, FakeRequest(GET, "/zones/process/feeds")
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation PRODUCED!!!!!", contentAsString(request))

    }

  }
}
