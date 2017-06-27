package controllers.users

import javax.inject.Singleton

import domain.security.TokenFailException
import domain.users.UserImages
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.users.UserImageService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserImageCtrl extends InjectedController {
  private val service = UserImageService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[UserImages](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(org: String, emailId: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("org" -> org, "emailId" -> emailId, "id" -> id)
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getUserImageById(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAlluserImages(org: String, emailId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("org" -> org, "emailId" -> emailId)
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getAllUserImages(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAllOrgImages(org: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getAllUserCompanyImages(org)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
