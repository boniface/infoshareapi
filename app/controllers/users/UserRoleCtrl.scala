package controllers.users

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.users.{User, UserRole}
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.users.{UserRoleService, UserService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserRoleCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
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

  def getById(siteId: String,emailId:String)  = Action.async {
    implicit request: Request[AnyContent] =>
      val resp: Future[UserRole] = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        user <-UserService.getSiteUser(emailId,siteId)
        results <- service.getUserRole(user.getOrElse(User.identity))
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll(siteId: String,emailId:String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
       user <-UserService.getSiteUser(emailId,siteId)
        results <- service.getUserRoles(user.getOrElse(User.identity))
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def delete(siteId: String,emailId:String) = Action.async {
    implicit request: Request[AnyContent] =>

      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        user <-UserService.getSiteUser(emailId,siteId)
        results <- service.deleteUserRoles(user.getOrElse(User.identity))
      } yield results
      resp.map(msg => Ok(Json.toJson(msg.isExhausted))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
