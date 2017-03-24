package controllers.links

import domain.feeds.Feed
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/03/21.
  */
class LinksControllerTest extends PlaySpec with GuiceOneAppPerTest {

  "LinksController" should {


    "Process Feeds" in {
      val zone = Feed("ZM", "MS", "1", "https://www.themastonline.com/feed/", "RSS", "NONE")
      val request = route(app, FakeRequest(POST, "/zones/feed/create")
        .withJsonBody(Json.toJson(zone))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }


  }
}
