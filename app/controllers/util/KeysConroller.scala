package controllers.util

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.util.Keys
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.util.KeysService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/03/12.
  */
class KeysConroller extends Controller {

  def create = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Keys](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- KeysService.save(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }


  def getKeyById(id: String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- KeysService.getKeyById(id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getKeys = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- KeysService.getAllkeys
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }
}
