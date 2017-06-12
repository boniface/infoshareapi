package controllers.util

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.util.Keys
import play.api.libs.json.Json
import play.api.mvc._
import services.util.KeysService
import javax.inject.Singleton

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class KeysController extends InjectedController {

  def create = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[Keys](input).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- KeysService.save(entity)
    } yield results
    response
      .map(_ => Ok(Json.toJson(entity)))
      .recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getKeyById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val response = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- KeysService.getKeyById(id)
      } yield results
      response
        .map(ans => Ok(Json.toJson(ans)))
        .recover {
          case _: TokenFailExcerption => Unauthorized
          case _: Exception => InternalServerError
        }
  }

  def getKeys = Action.async { implicit request: Request[AnyContent] =>
    val response = for {
      _ <- TokenCheck.getTokenfromParam(request)
      results <- KeysService.getAllkeys
    } yield results
    response
      .map(ans => Ok(Json.toJson(ans)))
      .recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
