package controllers.util

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.util.Roles
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.util.RolesService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/03/04.
  */
class RoleController extends Controller {

  def create = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Roles](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- RolesService.save(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }


  def getRoleById(id: String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- RolesService.getRoleById(id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getRoles = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- RolesService.getRoles
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }
}
