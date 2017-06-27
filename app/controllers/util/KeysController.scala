package controllers.util

import javax.inject.Singleton

import domain.security.TokenFailException
import domain.util.Keys
import play.api.libs.json.Json
import play.api.mvc._
import services.security.TokenCheckService
import services.util.KeysService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class KeysController extends InjectedController {

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[Keys](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- KeysService.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getKeyById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val response = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- KeysService.getKeyById(id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getKeys = Action.async { implicit request: Request[AnyContent] =>
    val response = for {
      _ <- TokenCheckService.apply.getTokenfromParam(request)
      results <- KeysService.getAllkeys
    } yield results
    response.map(ans => Ok(Json.toJson(ans))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }
}
