package controllers.location

import javax.inject.{Inject, Singleton}

import domain.location.AddressType
import domain.security.TokenFailException
import play.api.libs.json._
import play.api.mvc._
import services.location.AddressTypeService
import services.security.TokenCheckService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AddressTypeCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  val service = AddressTypeService

  def create = Action.async(parse.json) { request =>
    val entity = Json.fromJson[AddressType](request.body).get
    val response = for {
      _ <- TokenCheckService.apply.getUserToken(request)
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
        _ <- TokenCheckService.apply.getUserToken(request)
        results <- service.getAddressById(id)
      } yield results
      resp.map(msg => Ok(Json.toJson(msg))).recover {
        case _: TokenFailException => Unauthorized
        case _: Exception => InternalServerError
      }
  }

  def getAll = Action.async { implicit request: Request[AnyContent] =>
    val resp = for {
      _ <- TokenCheckService.apply.getUserToken(request)
      results <- service.getAllAddresses
    } yield results
    resp.map(msg => Ok(Json.toJson(msg))).recover {
      case _: TokenFailException => Unauthorized
      case _: Exception => InternalServerError
    }
  }
}
