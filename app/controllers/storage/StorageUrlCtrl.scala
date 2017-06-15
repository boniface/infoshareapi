package controllers.storage

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.storage.StorageUrl
import play.api.libs.json._
import play.api.mvc._
import services.storage.StorageUrlService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class StorageUrlCtrl extends InjectedController {
  val service = StorageUrlService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[StorageUrl](request.body).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getLinkById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheck.getTokenfromParam(request)
      results <- service.getAllLinks
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }
}
