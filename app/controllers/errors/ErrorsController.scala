package controllers.errors

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.errors.ErrorReport
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.errors.ErrorReportService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/03/18.
  */
class ErrorsController extends Controller {

  def create = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[ErrorReport](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- ErrorReportService.save(entity) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }


  def getErrors(zone: String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- ErrorReportService.getErrorReport(zone) if auth.status == "VALID"
      } yield results
      response.map(ans => Ok(Json.toJson(ans.toList)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
