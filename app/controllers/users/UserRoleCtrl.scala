package controllers.users

import javax.inject.Singleton

import domain.security.TokenFailException
import domain.users.UserRole
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.users.UserRoleService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserRoleCtrl extends InjectedController {
  private val service = UserRoleService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[UserRole](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(emailId: String, roleId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("emailId" -> emailId, "roleId" -> roleId)
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getById(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll(emailId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getAll(emailId)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def delete(emailId: String, roleId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("emailId" -> emailId, "roleId" -> roleId)
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.delete(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg.isExhausted))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
