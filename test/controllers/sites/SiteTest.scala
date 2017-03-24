package controllers.sites

import domain.zones.Site
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
/**
  * Created by hashcode on 2017/02/18.
  */
class SiteTest extends PlaySpec with GuiceOneAppPerTest {

  "SiteController" should {


    "Create Site " in {

      val mast = Site("ZM", "MS","https://www.themastonline.com/","The MAST Online","NONE")
      val times = Site("ZM", "TZ","http://www.times.co.zm/","Times of Zambia","NONE")
      val dailynation = Site("ZM", "DN","https://www.dailynation.news/","Daily Nation","NONE")
      val qfm = Site("ZM", "QF","http://www.qfmzambia.com/","QFM Zambia","NONE")
      val damilymail = Site("ZM", "ZD","https://www.daily-mail.co.zm/","Zambia Daily Mail","NONE")
      val lusakatimes = Site("ZM", "LT","https://www.lusakatimes.com/","Lusaka Times","NONE")
      val zambiareports = Site("ZM", "ZR","https://zambiareports.com/","Zambia Reports","NONE")
      val zambiawathdog = Site("ZM", "ZW","https://www.zambiawatchdog.com/","Zambian Watchdog","NONE")
      val tunfweko =  Site("ZM", "TF","https://www.tumfweko.com/","Tunfweko","NONE")
      val znbc =  Site("ZM", "ZB","http://www.znbc.co.zm/","ZNBC","NONE")
      val sites = List(mast,times,dailynation,qfm,damilymail,lusakatimes,zambiareports,zambiawathdog,tunfweko,znbc)

      sites foreach( site =>{
        val request = route(app, FakeRequest(POST, "/zones/site/create")
          .withJsonBody(Json.toJson(site))
          .withHeaders(AUTHORIZATION -> "Token")
        ).get
        status(request) mustBe OK
        contentType(request) mustBe Some("application/json")
        println(" The Content is ", contentAsString(request))
      })
    }

    "display  Sites " in {
      val id = 0
      val request = route(app, FakeRequest(GET, "/zones/site/start/" + id)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))
    }

    "display All ZONE Sites  " in {
      val zone = "ZM"
      val request = route(app, FakeRequest(GET, "/zones/site/all/"+zone)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))
    }

    "display ghet One Site   " in {
      val zone = "ZM"
      val site ="LT"

      val request = route(app, FakeRequest(GET, "/zones/site/"+zone+"/"+site)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))
    }

    "Update One Site " in {
      val tunfweko =  Site("ZM", "TF","https://www.tumfweko.com/","Tunfweko","NO LOGO")
      val request = route(app, FakeRequest(POST, "/zones/site/update")
        .withJsonBody(Json.toJson(tunfweko))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))
    }

  }
}
