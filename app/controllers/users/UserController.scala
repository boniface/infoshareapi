package controllers.users

import javax.inject.Singleton

import domain.security.TokenFailException
import domain.users.User
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.users.UserService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserController extends InjectedController {

  private val service = UserService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[User](request.body).get
    val resp = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- service.saveOrUpdate(entity)
    } yield results
    resp.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getUserByEmail(siteId: String, email: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getSiteUser(email,siteId)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getUserAccounts(email: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getUserAccounts(email)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getSiteUsers(siteUsers: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getSiteUsers(siteUsers)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
