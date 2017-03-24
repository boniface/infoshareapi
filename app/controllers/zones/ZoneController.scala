package controllers.zones

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.zones.Zone
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.zones.ZoneService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/02/18.
  */
class ZoneController extends Controller{

  def createZone = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Zone](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- ZoneService.SaveOrUpdate(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def updateZone = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Zone](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- ZoneService.SaveOrUpdate(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getZoneById(id:String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- ZoneService.getZoneById(id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }


  def getAllZones = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- ZoneService.getAllZones
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getAllActiveZones = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- ZoneService.getActiveZones
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getAllDisabledZones = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- ZoneService.getDisabledZones
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def deleteZone = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Zone](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- ZoneService.getAllZones
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
