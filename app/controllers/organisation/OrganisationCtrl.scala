package controllers.organisation

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailException}
import domain.organisation.Organisation
import play.api.libs.json._
import play.api.mvc._
import services.organisation.OrganisationService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class OrganisationCtrl extends InjectedController {
  val service = OrganisationService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[Organisation](request.body).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getAll
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

}
