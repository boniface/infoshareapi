package controllers.users

import conf.security.{Crediential, TokenCheck, TokenFailExcerption}
import domain.users.{User, UserRole}
import play.api.libs.json.Json
import play.api.mvc.{AnyContent, InjectedController, Request}
import services.users.UserCreationService
import javax.inject.Singleton
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserCreationController extends InjectedController {

  def createUser(roleId: String) = Action.async(parse.json) { request =>
    val input = request.body

    val entity = Json.fromJson[User](input).get
    val role = UserRole(entity.email, roleId, "ACTIVE")
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- UserCreationService.apply.createUser(entity, role)
    } yield results
    response
      .map(_ => Ok(Json.toJson(entity)))
      .recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def updateUser = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[User](input).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- UserCreationService.apply.updateUser(entity)
    } yield results
    response
      .map(_ => Ok(Json.toJson(entity)))
      .recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def registerUser = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[User](input).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- UserCreationService.apply.registerUser(entity)
    } yield results
    response
      .map(_ => Ok(Json.toJson(entity)))
      .recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def login = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[Crediential](input).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- UserCreationService.apply.loginUser(entity)
    } yield results
    response
      .map(ans => Ok(Json.toJson(ans)))
      .recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def isRegistered = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[User](input).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- UserCreationService.apply.isUserRegistered(entity)
    } yield results
    response
      .map(ans => Ok(Json.toJson(ans)))
      .recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getUser(email: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val response = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- UserCreationService.apply.getUser(email)
      } yield results
      response
        .map(ans => Ok(Json.toJson(ans)))
        .recover {
          case _: TokenFailExcerption => Unauthorized
          case _: Exception => InternalServerError
        }
  }
}
