package controllers.old.util

import domain.old.util.{KeyValues, Keys}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by hashcode on 2017/03/12.
  */
class KeysControllerTest extends PlaySpec with GuiceOneAppPerTest {

  "KeysController" should {

    "Create Key " in {
      val key = Keys(KeyValues.HOST,"smtp.gmail.com","ACTIVE")
      val request = route(app, FakeRequest(POST, "/util/keys/create")
        .withJsonBody(Json.toJson(key))
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content is ", contentAsString(request))

    }


    "Get a Single Key " in {
      val id = KeyValues.HOST
      val request = route(app, FakeRequest(GET, "/util/keys/" + id)
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))

    }

    "Get ALL Keys " in {
      val request = route(app, FakeRequest(GET, "/util/keys/all")
        .withHeaders(AUTHORIZATION -> "Token")
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println(" The Content this is organisation users", contentAsString(request))
    }
  }
}

