package controllers.util

import conf.security.{TokenCheck, TokenFailException}
import domain.util.Mail
import play.api.libs.json.Json
import play.api.mvc._
import services.util.MailService
import javax.inject.Singleton

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class MailSettingsController extends InjectedController {

  def create = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[Mail](input).get
    val response = for {
      _ <- TokenCheck.getToken(request)
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
        _ <- TokenCheck.getTokenfromParam(request)
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
        _ <- TokenCheck.getTokenfromParam(request)
        results <- MailService.getAllMailSettings(siteId)
      } yield results
      response.map(ans => Ok(Json.toJson(ans))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
