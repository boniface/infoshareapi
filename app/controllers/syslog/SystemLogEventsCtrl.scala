package controllers.syslog

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailException}
import domain.syslog.SystemLogEvents
import play.api.libs.json._
import play.api.mvc._
import services.syslog.SyslogService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class SystemLogEventsCtrl extends InjectedController {
  val service = SyslogService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[SystemLogEvents](request.body).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(org: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("org" -> org, "id" -> id)
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getById(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll(org: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getAll(org)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
