package api.votes

import domain.votes.Vote
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import util.{TestUtils, factories}

class VoteCtrlTest extends PlaySpec with BeforeAndAfter with GuiceOneAppPerTest {

  var entity : Vote = _
  val baseUrl = "/votes/"
  val title = "vote"

  before {
    entity = factories.getVote
  }

  title + " Controller " should {

    "castvotes a "+ title + "and return true if deleted or vote obj if created" in {
      val request = route(app, FakeRequest(POST, baseUrl + "castvote")
        .withJsonBody(toJson(entity))
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      if (status(request) equals OK){
        println("deleted")
        assert(contentAsString(request) equals "true")
      }

      if (status(request) equals CREATED) {
        println("created")
        assert(contentAsJson(request) equals toJson(entity))
      }
    }

    "get "+ title + " by id" in {
      val request = route(app, FakeRequest(GET, baseUrl + entity.siteId + "/" + entity.itemId + "/" + entity.ipAddress)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      assert(status(request) equals OK)
      println(contentAsString(request))
      assert(contentAsString(request).nonEmpty)
    }

    "get all " + title + "s" in {
      val request = route(app, FakeRequest(GET, baseUrl + entity.siteId + "/" + entity.itemId)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      println(contentAsString(request))
      assert(status(request) equals OK)
      assert(contentAsString(request).nonEmpty)
    }

    "get " + title + "s by status" in {
      val v_status = "LIKED"
      val request = route(app, FakeRequest(GET, baseUrl + "status/" + entity.siteId + "/" + entity.itemId + "/" + v_status)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      println(status(request))
      println(contentAsString(request))
      assert(status(request) equals OK)
    }

    "get all user " + title + "s by status" in {
      val v_status = "LIKED"
      val request = route(app, FakeRequest(GET, baseUrl + "user/status/" + entity.siteId + "/" + entity.itemId + "/" + v_status)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get


      println(contentAsString(request))
      assert(status(request) equals OK)
      assert(contentAsString(request).nonEmpty)

    }

    "get all user " + title + "s" in {
      val request = route(app, FakeRequest(GET, baseUrl + "user/" + entity.siteId + "/" + entity.itemOwnerId)
        .withHeaders(TestUtils.getHeaders: _*)
      ).get

      println(contentAsString(request))
      assert(status(request) equals OK)
      assert(contentAsString(request).nonEmpty)
    }
  }
}
