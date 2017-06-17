package controllers.content

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailException}
import domain.content.RawContent
import play.api.libs.json._
import play.api.mvc._
import services.content.RawContentService
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class RawContentCtrl extends InjectedController {
  private val service = RawContentService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[RawContent](request.body).get
    val resp = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    resp.map(_ => Ok(Json.toJson(entity))).recover {
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

  def getPaginated(org: String, start_value: Int) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getPaginatedContents(org, start_value)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg.toSeq))).recover {
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
