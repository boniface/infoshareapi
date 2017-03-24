package controllers.zones

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.zones.Site
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.zones.SiteService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/02/18.
  */
class SiteController extends Controller {

  def create = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Site](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- SiteService.save(entity)
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
      val entity = Json.fromJson[Site](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- SiteService.save(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getSiteById(id: String, site: String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- SiteService.getSiteById(id, site)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getSiteByNumber(start: Int) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- SiteService.getSitesByNumber(start, 50) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(ans.toSeq)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getSiteByZones(zone: String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- SiteService.getSitesByZone(zone)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
