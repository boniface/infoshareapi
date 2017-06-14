package controllers.users

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.users.ValidUser
import play.api.libs.json.Json
import play.api.mvc._
import services.users.ValidUserService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ValidUserController extends InjectedController {

  val validUserService: ValidUserService = ValidUserService

  def create = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[ValidUser](input).get

    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- ValidUserService.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def isUserValid(userId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      validUserService.isUserValid(userId) map { msg =>
        Ok(Json.toJson(msg))
      }
  }

  def getValidUserEvents(userId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      validUserService.getValidUserEvents(userId).map { msg =>
        Ok(Json.toJson(msg))
      }
  }

  def getValidUsers = Action.async { implicit request: Request[AnyContent] =>
    validUserService.getValidUsers.map { msg =>
      Ok(Json.toJson(msg))
    }
  }
}
