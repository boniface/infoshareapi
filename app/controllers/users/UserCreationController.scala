package controllers.users

import java.time.LocalDateTime
import javax.inject.Singleton

import domain.security.{Credential, TokenFailException, UserGeneratedToken}
import domain.users.{User, UserRole}
import play.api.libs.json.Json
import play.api.mvc.{AnyContent, InjectedController, Request}
import services.security.{LoginService, TokenCheckService}
import services.users.UserCreationService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserCreationController extends InjectedController {

  def createUser(roleId: String) = Action.async(parse.json) { request =>
    val entity = Json.fromJson[User](request.body).get
    val role = UserRole(entity.email, LocalDateTime.now, roleId)
    val response: Future[Boolean] = for {
      _ <- TokenCheckService.apply.getToken(request)
      results: Boolean <- UserCreationService.apply.createUser(entity, role)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def updateUser = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[User](input).get
    val response: Future[Boolean] = for {
      _ <- TokenCheckService.apply.getToken(request)
      results: Boolean <- UserCreationService.apply.updateUser(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def registerUser = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[User](input).get
    val response: Future[Boolean] = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- UserCreationService.apply.registerUser(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def login = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[Credential](input).get
    val agent = request.headers.toSimpleMap.getOrElse("User-Agent", "")
    val siteId = request.headers.get("SiteID").getOrElse("")
    val response: Future[UserGeneratedToken] = for {
      _ <- TokenCheckService.apply.getToken(request)
      results: UserGeneratedToken <- LoginService.apply.createNewToken(entity, agent, siteId)
    } yield results
    response.map(ans => Ok(Json.toJson(ans))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def isRegistered = Action.async(parse.json) { request =>
    val input = request.body
    val entity = Json.fromJson[User](input).get
    val response: Future[Boolean] = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- UserCreationService.apply.isUserRegistered(entity)
    } yield results
    response.map(ans => Ok(Json.toJson(ans))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getUser(email: String) = Action.async {
    implicit request: Request[AnyContent] =>
     val siteId = request.headers.get("SiteID").getOrElse("")
      val response: Future[Option[User]] = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results: Option[User] <- UserCreationService.apply.getUser(email,siteId)
      } yield results
      response.map(ans => Ok(Json.toJson(ans))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

}
