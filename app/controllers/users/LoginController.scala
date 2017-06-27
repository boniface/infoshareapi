package controllers.users

import domain.security.{Credential, TokenFailException, UserGeneratedToken}
import domain.users.{Login, User}
import play.api.libs.json.Json
import play.api.mvc.{AnyContent, InjectedController, Request}
import services.security.{LoginService, TokenCheckService}
import services.users.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by hashcode on 6/27/17.
  */
class LoginController extends InjectedController{

  def authenticate = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[Credential](input).get
    val agent = request.headers.toSimpleMap.getOrElse("User-Agent", "")
    val response: Future[UserGeneratedToken] = for {
      results: UserGeneratedToken <- LoginService.apply.createNewToken(entity, agent)
    } yield results
    response.map(ans => Ok(Json.toJson(ans))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def login(email: String) = Action.async {
    implicit request: Request[AnyContent] =>

      val response: Future[Seq[Login]] = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results<- LoginService.apply.getLogins(email)
      } yield results
      response.map(ans => Ok(Json.toJson(ans))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

}
