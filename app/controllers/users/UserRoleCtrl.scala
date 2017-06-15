package controllers.users

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.users.UserRole
import play.api.libs.json._
import play.api.mvc._
import services.users.UserRoleService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserRoleCtrl extends InjectedController {
  val service = UserRoleService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[UserRole](request.body).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(emailId: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("emailId" -> emailId, "id" -> id)
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getRolesByemailId(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll(emailId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getUserRole(emailId)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def detete(emailId: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("emailId" -> emailId, "id" -> id)
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.deleteRole(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg.isExhausted))).recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
