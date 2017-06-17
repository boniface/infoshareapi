package controllers.content

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailException}
import domain.content.Media
import play.api.libs.json._
import play.api.mvc._
import services.content.MediaService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class MediaCtrl extends InjectedController {
  private val service = MediaService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[Media](request.body).get
    val resp = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    resp.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(content_Id: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("contentId" -> content_Id, "id" -> id)
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getById(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll(content_id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getAll(content_id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
