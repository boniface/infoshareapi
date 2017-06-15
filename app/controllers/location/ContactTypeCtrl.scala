package controllers.location

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailExcerption}
import domain.location.ContactType
import play.api.libs.json._
import play.api.mvc._
import services.location.ContactTypeService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ContactTypeCtrl extends InjectedController {
  val service = ContactTypeService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[ContactType](request.body).get
    val response = for {
      _ <- TokenCheck.getToken(request)
      results <- service.save(entity)
    } yield results
    response.map(_ => Ok(Json.toJson(entity))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }

  def getById(id: String) = Action.async {
    implicit request: Request[AnyContent] =>
      val resp = for {
        _ <- TokenCheck.getTokenfromParam(request)
        results <- service.getContactById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailExcerption => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheck.getTokenfromParam(request)
      results <- service.getAllContactType
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailExcerption => Unauthorized
      case _: Exception => InternalServerError
    }
  }
}
