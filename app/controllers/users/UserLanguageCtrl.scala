package controllers.users

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.users.UserLanguage
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.users.UserLanguageService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserLanguageCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  private val service = UserLanguageService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[UserLanguage](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(emailId: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("emailId" -> emailId, "id" -> id)
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getUserLangById(args)
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
        results <- service.getAllUserLang(emailId)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
