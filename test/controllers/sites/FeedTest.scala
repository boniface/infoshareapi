package controllers.sites

import domain.feeds.Feed
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
/**
  * Created by hashcode on 2017/03/18.
  */
class FeedTest extends PlaySpec with GuiceOneAppPerTest {

  "FeedController" should {


    "Create Feed " in {
      val zone = Feed("ZM", "MS", "1", "https://staging.themastonline.com/feed/", "RSS","NONE")
      val request = route(app, FakeRequest(POST, "/zones/feed/create")
        .withJsonBody(Json.toJson(zone))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }

    "Create Another Feed " in {
      val zone = Feed("ZM", "ZB", "1", "http://www.znbc.co.zm/?feed=rss2", "RSS","NONE")
      val request = route(app, FakeRequest(POST, "/zones/feed/create")
        .withJsonBody(Json.toJson(zone))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }


    "display Feed type" in {
      val id = "1"
      val zone="ZM"
      val site="MS"
      val request = route(app, FakeRequest(GET, "/zones/feeds/site/get/"+zone+"/"+site+"/"+id)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))

    }

    "display Feeds type" in {
      val ZM="ZM"

      val request = route(app, FakeRequest(GET, "/zones/feeds/"+ZM)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is THE FEEDS!!!!!!", contentAsString(request))

    }

    "Update Feed " in {
     val feed = Feed("ZM", "TZ", "1", "http://www.times.co.zm/?feed=rss2", "RSS","NONE")

      val request = route(app, FakeRequest(POST, "/zones/feed/update")
        .withJsonBody(Json.toJson(feed))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }

  }
}
