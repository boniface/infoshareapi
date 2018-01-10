package controllers.users

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.users.ValidUser
import play.api.libs.json.Json
import play.api.mvc._
import services.security.TokenCheckService
import services.users.ValidUserService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ValidUserController @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {

  private val validUserService: ValidUserService = ValidUserService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[ValidUser](request.body).get

    val response = for {
      _ <- TokenCheckService.apply.getUserToken(request)
      results <- ValidUserService.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def isUserValid(siteId:String, userId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      validUserService.isUserValid(siteId,userId) map { msg =>
        Ok(Json.toJson(msg))
      }
  }

  def getValidUserEvents(siteId:String,userId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      validUserService.getValidUserEvents(siteId,userId).map { msg =>
        Ok(Json.toJson(msg))
      }
  }

  def getValidUsers = Action.async { implicit request: Request[AnyContent] =>
    validUserService.getValidUsers.map { msg =>
      Ok(Json.toJson(msg))
    }
  }

}
