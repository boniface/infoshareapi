package controllers.util

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.util.Mail
import play.api.libs.json.Json
import play.api.mvc._
import services.mail.MailService
import services.security.TokenCheckService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class MailSettingsController @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {

  def create = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[Mail](input).get
    val response = for {
      _ <- TokenCheckService.apply.getUserToken(request)
      results <- MailService.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getMailSettingById(siteId: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val response = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- MailService.getMailSettingById(siteId, id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getSiteMailSettings(siteId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val response = for {
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- MailService.getAllMailSettings(siteId)
      } yield results
      response.map(ans => Ok(Json.toJson(ans))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
