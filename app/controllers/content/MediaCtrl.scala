package controllers.content

import javax.inject.{Inject, Singleton}

import domain.content.Media
import domain.security.TokenFailException
import play.api.libs.json._
import play.api.mvc._
import services.content.MediaService
import services.security.TokenCheckService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class MediaCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  private val service = MediaService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[Media](request.body).get
    val resp = for {
      _ <- TokenCheckService.apply.getUserToken(request)
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
        _ <- TokenCheckService.apply.getUserToken(request)
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
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getAll(content_id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
