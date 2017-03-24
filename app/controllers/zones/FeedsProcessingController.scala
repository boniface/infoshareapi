package controllers.zones

import conf.security.{TokenCheck, TokenFailExcerption}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/03/21.
  */
class FeedsProcessingController extends Controller{

  def processFeeds = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- FeedsProcessingService.apply.processFeeds
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
