package controllers.zones

import conf.security.{TokenCheck, TokenFailExcerption}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/02/18.
  */
class FeedController extends Controller {

  def create = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Feed](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- FeedsService.save(entity) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def update = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Feed](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- FeedsService.save(entity) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }



  def getZoneFeeds(zone:String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- FeedsService.getZoneFeeds(zone) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getSiteFeeds(zone:String, sitecode:String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- FeedsService.getSiteFeeds(zone,sitecode) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getFeedById(zone:String, sitecode:String,id:String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- FeedsService.getFeedById(zone,sitecode,id) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def deleteFeed(zone:String, sitecode:String,id:String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- FeedsService.deleteFeed(zone,sitecode,id) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(ans.isExhausted)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
