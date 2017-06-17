package controllers.location

import javax.inject.Singleton

import conf.security.{TokenCheck, TokenFailException}
import domain.location.AddressType
import play.api.libs.json._
import play.api.mvc._
import services.location.AddressTypeService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AddressTypeCtrl extends InjectedController {
  val service = AddressTypeService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[AddressType](request.body).get
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
        results <- service.getAddressById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheck.getTokenfromParam(request)
      results <- service.getAllAddresses
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }
}
