package controllers.util

import java.util.Date

import domain.util.Mail
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/03/12.
  * siteId: String,
                id: String,
                key: String,
                value: String,
                host: String,
                port: String,
                state: String,
                date: Date
  */
class MailSettingsControllerTest extends PlaySpec with GuiceOneAppPerTest {

  "MailSettingsController" should {

    "Create Mail Setting " in {
      val key = Mail("HASHCODE","SITE","test@test.com","value","smtp.gmail.com","8080","ACTIVE",new Date() )
      val request = route(app, FakeRequest(POST, "/util/mail/create")
        .withJsonBody(Json.toJson(key))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }


    "Get a Single MAil Setting " in {
      val siteId = "HASHCODE"
      val id = "HASHCODE"
      val request = route(app, FakeRequest(GET, "/util/mail/" +siteId+"/"+id)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))

    }

    "Get Site Mail Settings  " in {
      val siteId = "HASHCODE"
      val request = route(app, FakeRequest(GET, "/util/mail/all/"+siteId)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))
    }
  }
}

