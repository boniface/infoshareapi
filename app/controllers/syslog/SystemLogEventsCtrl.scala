package controllers.syslog

import javax.inject.{Inject, Singleton}

import domain.security.TokenFailException
import domain.syslog.SystemLogEvents
import play.api.libs.json._
import play.api.mvc._
import services.security.TokenCheckService
import services.syslog.SyslogService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class SystemLogEventsCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  val service = SyslogService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[SystemLogEvents](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(org: String, id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val args = Map("siteId" -> org, "id" -> id)
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getById(args)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll(siteId: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheckService.apply.getTokenfromParam(request)
        results <- service.getAll(siteId)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }
}
