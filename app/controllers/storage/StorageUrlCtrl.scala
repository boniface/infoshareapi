package controllers.storage

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.storage.StorageUrl
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.storage.StorageUrlService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class StorageUrlCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  val service = StorageUrlService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[StorageUrl](request.body).get
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
        results <- service.getLinkById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheckService.apply.getUserToken(request)
      results <- service.getAllLinks
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }
}
