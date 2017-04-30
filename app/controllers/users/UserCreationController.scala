package controllers.users

import conf.security.{Crediential, TokenCheck, TokenFailExcerption}
import domain.users.{User, UserRole}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.users.UserCreationService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2017/03/04.
  */
class UserCreationController extends Controller {

  def createUser(roleId: String) = Action.async(parse.json) {
    request =>
      val input = request.body

      val entity = Json.fromJson[User](input).get
      val role = UserRole(entity.email,  roleId,"ACTIVE")
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- UserCreationService.apply.createUser(entity, role)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def updateUser = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[User](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- UserCreationService.apply.updateUser(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def registerUser = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[User](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- UserCreationService.apply.registerUser(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def login = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Crediential](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- UserCreationService.apply.loginUser(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def isRegistered = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[User](input).get
      val response = for {
        auth <- TokenCheck.getToken(request)
        results <- UserCreationService.apply.isUserRegistered(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }


  def getUser(email:String) = Action.async {
    request =>
      val input = request.body
      val response = for {
        auth <- TokenCheck.getTokenfromParam(request)
        results <- UserCreationService.apply.getUser(email)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }
}
