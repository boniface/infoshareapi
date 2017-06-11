package controllers.users

import javax.inject.{Inject, Singleton}

import domain.users.ValidUser
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import services.users.ValidUserService

import scala.concurrent.ExecutionContext

/**
  * Created by hashcode on 2017/06/11.
  */
@Singleton
class ValidUserController @Inject()
(implicit exec: ExecutionContext,
 cc: ControllerComponents
) extends AbstractController(cc) {

  val validUserService: ValidUserService = ValidUserService


  def create = Action.async(parse.json) {
    (request: Request[JsValue]) =>
      val input = request.body
      val entity = Json.fromJson[ValidUser](input).get

      val response = for {
        results <- ValidUserService.save(entity)
      } yield results
      response.map(_ => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: Exception => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def isUserValid(userId: String) = Action.async {
    implicit request: Request[AnyContent] =>

      validUserService.isUserValid(userId) map { msg => Ok(Json.toJson(msg)) }
  }

  def getValidUserEvents(userId: String) = Action.async {
    implicit request: Request[AnyContent] =>

      validUserService.getValidUserEvents(userId).map { msg => Ok(Json.toJson(msg)) }
  }

  def getValidUsers = Action.async {
    implicit request: Request[AnyContent] =>
      validUserService.getValidUsers.map { msg => Ok(Json.toJson(msg)) }
  }
}
