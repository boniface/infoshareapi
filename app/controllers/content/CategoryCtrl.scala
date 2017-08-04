package controllers.content

import javax.inject.{Inject, Singleton}

import domain.content.Category
import domain.security.TokenFailException
import org.jose4j.jwt.consumer.InvalidJwtException
import org.jose4j.lang.JoseException
import play.api.libs.json._
import play.api.mvc._
import services.content.CategoryService
import services.security.TokenCheckService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class CategoryCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  private val service = CategoryService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[Category](request.body).get
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
