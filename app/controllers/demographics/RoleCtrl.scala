package controllers.demographics

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.demographics.Role
import play.api.libs.json._
import play.api.mvc._
import services.demographics.RoleService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class RoleCtrl extends InjectedController {
  private val service = RoleService

  def create = Action.async(parse.json) { implicit request =>
    val entity = Json.fromJson[Role](request.body).get

    val resp = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    resp.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheck.getTokenfromParam(request)
      results <- service.getAll
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def delete(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.delete(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg.isExhausted))).recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

}
