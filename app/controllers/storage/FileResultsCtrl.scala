package controllers.storage

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.storage.FileResults
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.storage.FileResultsService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class FileResultsCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  val service = FileResultsService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[FileResults](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getToken(request)
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
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheckService.apply.getTokenfromParam(request)
      results <- service.getAll
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }
}
