package controllers.content

import javax.inject.{Inject, Singleton}

import domain.content.ContentType
import domain.security.TokenFailException
import play.api.libs.json._
import play.api.mvc._
import services.content.ContentTypeService
import services.security.TokenCheckService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ContentTypeCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  private val service = ContentTypeService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[ContentType](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getUserToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheckService.apply.getUserToken(request)
      results <- service.getAll
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

}
