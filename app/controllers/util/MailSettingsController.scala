package controllers.util

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.util.Mail
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.util.MailService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/03/12.
  */
class MailSettingsController extends Controller {

  def create = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Mail](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- MailService.save(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }


  def getMailSettingById(siteId:String, id: String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- MailService.getMailSettingById(siteId,id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getSiteMailSettings(siteId:String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- MailService.getAllMailSettings(siteId)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }
}
